package br.com.jexperts.danilooa.crudpessoas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException;
import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException.IdentificadorNegocioExceptions;
import br.com.jexperts.danilooa.crudpessoas.jpa.IdentificadorQueries;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;

@Stateless
public class PessoaServiceEJB implements PessoaService {

    @Inject
    private EntityManager entityManager;

    @Override
    public void inserir(Pessoa pessoa) throws NegocioException {
	if (contarPessoasComMesmoCpf(pessoa.getId(), pessoa.getCpf()) > 0) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PESSOAS_COM_CPFS_IGUAIS);
	}
	if (mesmaPaternidade(pessoa.getPai(), pessoa.getMae())) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PAIS_SAO_IRMAOS);
	}

	if (paiEMaeIguais(pessoa)) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PAI_E_MAE_IGUAIS);
	}

	if (paiOuMaeSaoIguaisFilho(pessoa)) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PAI_OU_MAE_SAO_IGUAIS_FILHO);
	}

	if (pessoa.getId() == null) {
	    entityManager.persist(pessoa);
	} else {
	    entityManager.merge(pessoa);
	}
	entityManager.flush();
    }

    private Boolean paiEMaeIguais(Pessoa pessoa) {
	if (pessoa.getPai() == null || pessoa.getMae() == null) {
	    return false;
	}
	return pessoa.getPai().equals(pessoa.getMae());
    }

    private Boolean paiOuMaeSaoIguaisFilho(Pessoa pessoa) {
	if (pessoa.getPai() == null && pessoa.getMae() == null) {
	    return false;
	}

	if (pessoa.equals(pessoa.getPai())) {
	    return true;
	}

	if (pessoa.equals(pessoa.getMae())) {
	    return true;
	}

	return false;
    }

    private Boolean mesmaPaternidade(Pessoa pai, Pessoa mae) {

	// Pai nao pode ser igual o pai do Mae
	if (pai != null && mae != null && pai.equals(mae.getPai())) {
	    return true;
	}

	// Mae nao pode ser igual a mae da Pai
	if (mae != null && pai != null && mae.equals(pai.getMae())) {
	    return true;
	}

	// Mae nao pode ser igual ao pai do Pai
	if (mae != null && pai != null && mae.equals(pai.getPai())) {
	    return true;
	}

	return false;
    }

    private Long contarPessoasComMesmoCpf(Long idPessoa, String cpf) {

	Query query = null;

	if (idPessoa == null) {
	    query = entityManager.createNamedQuery(IdentificadorQueries.CONTA_PESSOAS_MESMO_CPF.name());
	    query.setParameter("cpf", cpf);
	}

	if (idPessoa != null) {
	    query = entityManager.createNamedQuery(IdentificadorQueries.CONTA_PESSOAS_MESMO_CPF_QUANDO_PESSOA_POSSUI_ID.name());
	    query.setParameter("idPessoa", idPessoa);
	    query.setParameter("cpf", cpf);
	}

	return (Long) query.getSingleResult();
    }

    @Override
    public Boolean possuiFilhos(Long idPessoa) {

	Query query = entityManager.createNamedQuery(IdentificadorQueries.CONTA_FILHOS_PESSOA.name());
	query.setParameter("idPai", idPessoa);
	Long quantidadeFilhos = (Long) query.getSingleResult();

	if (quantidadeFilhos > 0) {
	    return true;
	}

	return false;
    }

    @Override
    public void excluir(Pessoa pessoa) {

	Query queryListaFilhosPessoa = entityManager.createNamedQuery(IdentificadorQueries.LISTA_FILHOS_PESSOA.name());
	queryListaFilhosPessoa.setParameter("idPai", pessoa.getId());

	List<Pessoa> listFilhosPessoa = queryListaFilhosPessoa.getResultList();

	for (Pessoa filho : listFilhosPessoa) {
	    excluir(filho);
	}
	pessoa = entityManager.find(Pessoa.class, pessoa.getId());
	entityManager.remove(pessoa);
    }

    @Override
    public List<Pessoa> listarPorNome(String nome, Integer quantidadeMaximaDeRegistros, Pessoa... pessoaASerIgnorada) {
	Query queryListaPessoasPorNome = entityManager.createNamedQuery(IdentificadorQueries.LISTA_PESSOAS_POR_NOME.name());
	queryListaPessoasPorNome.setParameter("nomeCompleto", "%" + nome.toUpperCase() + "%");
	if (quantidadeMaximaDeRegistros != null) {
	    queryListaPessoasPorNome.setMaxResults(quantidadeMaximaDeRegistros);
	}

	List<Pessoa> listFilhosPessoa = queryListaPessoasPorNome.getResultList();

	for (Pessoa pessoaASerRemovida : pessoaASerIgnorada) {
	    if (pessoaASerRemovida == null || pessoaASerRemovida.getId() == null) {
		continue;
	    }
	    listFilhosPessoa.remove(pessoaASerRemovida);
	}
	return listFilhosPessoa;
    }

    @Override
    public List<Pessoa> listarPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
	Query queryListagemPessoas = montarQueryParaContarOuListarPessoas(filtroListagemPessoasDTO, false);
	definirParametrosQuery(filtroListagemPessoasDTO, queryListagemPessoas);
	return queryListagemPessoas.getResultList();
    }

    private void definirParametrosQuery(FiltroListagemPessoasDTO filtroListagemPessoasDTO, Query query) {
	if (filtroListagemPessoasDTO.getNomePessoa() != null && !filtroListagemPessoasDTO.getNomePessoa().trim().isEmpty()) {
	    query.setParameter("nomePessoa", "%" + filtroListagemPessoasDTO.getNomePessoa().toUpperCase() + "%");
	}
	if (filtroListagemPessoasDTO.getCpf() != null && !filtroListagemPessoasDTO.getCpf().trim().isEmpty()) {
	    query.setParameter("cpf", filtroListagemPessoasDTO.getCpf());
	}
	if (filtroListagemPessoasDTO.getNomeMaeOuPai() != null && !filtroListagemPessoasDTO.getNomeMaeOuPai().trim().isEmpty()) {
	    query.setParameter("nomeMaeOuPai", "%" + filtroListagemPessoasDTO.getNomeMaeOuPai().toUpperCase() + "%");
	}
	if (filtroListagemPessoasDTO.getDataInicialAniversario() != null) {
	    query.setParameter("dataInicialAniversario", filtroListagemPessoasDTO.getDataInicialAniversario());
	}
	if (filtroListagemPessoasDTO.getDataFinalAniversario() != null) {
	    query.setParameter("dataFinalAniversario", filtroListagemPessoasDTO.getDataFinalAniversario());
	}

    }

    @Override
    public Long contaTotalPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
	Query queryContagemRegistrosListagemPessoas = montarQueryParaContarOuListarPessoas(filtroListagemPessoasDTO, true);
	definirParametrosQuery(filtroListagemPessoasDTO, queryContagemRegistrosListagemPessoas);
	return (Long) queryContagemRegistrosListagemPessoas.getSingleResult();
    }

    private Query montarQueryParaContarOuListarPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO, Boolean deveContar) {
	String queryStringListagemPessoas = prepararQueryStringListagemPessoas(filtroListagemPessoasDTO, deveContar);
	Query queryListaPessoas = entityManager.createQuery(queryStringListagemPessoas);
	if (deveContar) {
	    return queryListaPessoas;
	}
	queryListaPessoas.setFirstResult(filtroListagemPessoasDTO.getIndexDoPrimeiraRegistroDaPagina());
	queryListaPessoas.setMaxResults(filtroListagemPessoasDTO.getQuantidadeDeRegistrosPorPagina());
	return queryListaPessoas;
    }

    private String prepararQueryStringListagemPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO, Boolean deveContar) {
	StringBuilder stringBuilderQueryListagemPessoas = new StringBuilder();
	stringBuilderQueryListagemPessoas.append("select ");
	String instrucaoParaListarOuContar = " p ";
	if (deveContar) {
	    instrucaoParaListarOuContar = " count(p) ";
	}
	stringBuilderQueryListagemPessoas.append(instrucaoParaListarOuContar);
	stringBuilderQueryListagemPessoas.append("from  ");
	stringBuilderQueryListagemPessoas.append("		Pessoa p ");

	if (deveContar) {
	    stringBuilderQueryListagemPessoas.append("		left join p.pai  pai ");
	    stringBuilderQueryListagemPessoas.append("		left join p.mae mae ");
	} else {
	    stringBuilderQueryListagemPessoas.append("		left join fetch p.pai pai ");
	    stringBuilderQueryListagemPessoas.append("		left join fetch p.mae mae ");
	}
	List<Object> listArgumentosPreenchidos = new ArrayList<>();
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getCpf());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getDataFinalAniversario());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getDataInicialAniversario());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getNomeMaeOuPai());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getNomePessoa());

	boolean todosArgumentosNulos = true;
	for (Object argumento : listArgumentosPreenchidos) {
	    if (argumento != null && !argumento.toString().trim().isEmpty()) {
		todosArgumentosNulos = false;
		break;
	    }
	}

	if (todosArgumentosNulos) {
	    return stringBuilderQueryListagemPessoas.toString();
	}
	stringBuilderQueryListagemPessoas.append(" Where ");
	if (filtroListagemPessoasDTO.getNomePessoa() != null && !filtroListagemPessoasDTO.getNomePessoa().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append("		upper(p.nomeCompleto) like :nomePessoa ");
	    stringBuilderQueryListagemPessoas.append("and");
	}
	if (filtroListagemPessoasDTO.getCpf() != null && !filtroListagemPessoasDTO.getCpf().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append("		p.cpf = :cpf ");
	    stringBuilderQueryListagemPessoas.append("and");
	}
	if (filtroListagemPessoasDTO.getNomeMaeOuPai() != null && !filtroListagemPessoasDTO.getNomeMaeOuPai().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append(" ( ");
	    stringBuilderQueryListagemPessoas.append("	upper(pai.nomeCompleto) like :nomeMaeOuPai ");
	    stringBuilderQueryListagemPessoas.append("	or ");
	    stringBuilderQueryListagemPessoas.append("	upper(mae.nomeCompleto) like :nomeMaeOuPai ");
	    stringBuilderQueryListagemPessoas.append(" ) ");
	    stringBuilderQueryListagemPessoas.append("and ");
	}
	if (filtroListagemPessoasDTO.getDataInicialAniversario() != null) {
	    stringBuilderQueryListagemPessoas.append("		p.dataNascimento >= :dataInicialAniversario ");
	    stringBuilderQueryListagemPessoas.append("and ");
	}
	if (filtroListagemPessoasDTO.getDataFinalAniversario() != null) {
	    stringBuilderQueryListagemPessoas.append("		p.dataNascimento <= :dataFinalAniversario ");
	    stringBuilderQueryListagemPessoas.append("and ");
	}

	stringBuilderQueryListagemPessoas.replace(stringBuilderQueryListagemPessoas.length() - 4, stringBuilderQueryListagemPessoas.length(), "");

	if (!deveContar) {
	    stringBuilderQueryListagemPessoas.append(" order by  ");
	    stringBuilderQueryListagemPessoas.append(" p.nomeCompleto  ");
	}
	return stringBuilderQueryListagemPessoas.toString();
    }

    @Override
    public Pessoa getPessoa(Long idPessoa) {
	Query queryListaPessoasPorNome = entityManager.createNamedQuery(IdentificadorQueries.OBTEM_PESSOA_POR_ID_COM_MAE_E_PAI.name());
	queryListaPessoasPorNome.setParameter("idPessoa", idPessoa);
	return (Pessoa) queryListaPessoasPorNome.getSingleResult();
    }

}
