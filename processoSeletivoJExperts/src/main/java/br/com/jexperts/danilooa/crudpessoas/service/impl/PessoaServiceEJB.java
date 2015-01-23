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
	if (contarPessoasComMesmoCpf(pessoa.getCpf()) > 0) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PESSOAS_COM_CPFS_IGUAIS);
	}
	if (mesmaPaternidade(pessoa.getPai(), pessoa.getMae())) {
	    throw new NegocioException(IdentificadorNegocioExceptions.PAIS_SAO_IRMAOS);
	}

	entityManager.persist(pessoa);
	entityManager.flush();
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

    private Long contarPessoasComMesmoCpf(String cpf) {
	Query query = entityManager.createNamedQuery(IdentificadorQueries.CONTA_PESSOAS_MESMO_CPF.name());
	query.setParameter("cpf", cpf);
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

	entityManager.remove(pessoa);
    }

    @Override
    public List<Pessoa> listarPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
	Query queryListagemPessoas = montarQueryParaContarOuListarPessoas(filtroListagemPessoasDTO, false);
	definirParametrosQuery(filtroListagemPessoasDTO, queryListagemPessoas);
	return queryListagemPessoas.getResultList();
    }

    private void definirParametrosQuery(FiltroListagemPessoasDTO filtroListagemPessoasDTO, Query query) {
	if (filtroListagemPessoasDTO.getNomePessoa() != null && !filtroListagemPessoasDTO.getNomePessoa().isEmpty()) {
	    query.setParameter("nomePessoa", "%" + filtroListagemPessoasDTO.getNomePessoa() + "%");
	}
	if (filtroListagemPessoasDTO.getCpf() != null && !filtroListagemPessoasDTO.getCpf().isEmpty()) {
	    query.setParameter("cpf", filtroListagemPessoasDTO.getCpf());
	}
	if (filtroListagemPessoasDTO.getNomeMaeOuPai() != null && !filtroListagemPessoasDTO.getNomeMaeOuPai().isEmpty()) {
	    query.setParameter("nomeMaeOuPai", filtroListagemPessoasDTO.getNomeMaeOuPai());
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
	List<Object> listArgumentosPreenchidos = new ArrayList<>();
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getCpf());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getDataFinalAniversario());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getDataInicialAniversario());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getNomeMaeOuPai());
	listArgumentosPreenchidos.add(filtroListagemPessoasDTO.getNomePessoa());

	boolean todosArgumentosNulos = true;
	for (Object argumento : listArgumentosPreenchidos) {
	    if (argumento != null) {
		todosArgumentosNulos = false;
		break;
	    }
	}
	
	if (todosArgumentosNulos) {
	    return stringBuilderQueryListagemPessoas.toString();
	}
	stringBuilderQueryListagemPessoas.append("Where ");
	if (filtroListagemPessoasDTO.getNomePessoa() != null && !filtroListagemPessoasDTO.getNomePessoa().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append("		p.nomeCompleto like :nomePessoa ");
	    stringBuilderQueryListagemPessoas.append("and");
	}
	if (filtroListagemPessoasDTO.getCpf() != null && !filtroListagemPessoasDTO.getCpf().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append("		p.cpf = :cpf ");
	    stringBuilderQueryListagemPessoas.append("and");
	}
	if (filtroListagemPessoasDTO.getNomeMaeOuPai() != null && !filtroListagemPessoasDTO.getNomeMaeOuPai().isEmpty()) {
	    stringBuilderQueryListagemPessoas.append(" ( ");
	    stringBuilderQueryListagemPessoas.append("	p.pai.nomeCompleto like :nomeMaeOuPai ");
	    stringBuilderQueryListagemPessoas.append("	or ");
	    stringBuilderQueryListagemPessoas.append("	p.mae.nomeCompleto like :nomeMaeOuPai ");
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
}
