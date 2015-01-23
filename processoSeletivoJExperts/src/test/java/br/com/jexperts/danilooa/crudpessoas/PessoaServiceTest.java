package br.com.jexperts.danilooa.crudpessoas;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.danilo.nailbar.databuilder.PessoaDataBuilder;
import br.com.jexperts.danilooa.crudpessoas.config.AppConfig;
import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.enums.GeneroEnum;
import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException;
import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException.IdentificadorNegocioExceptions;
import br.com.jexperts.danilooa.crudpessoas.jpa.IdentificadorQueries;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;
import br.com.jexperts.danilooa.crudpessoas.service.impl.PessoaServiceEJB;

@RunWith(Arquillian.class)
public class PessoaServiceTest extends TransactionalTestCase {

    @Inject
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
	return entityManager;
    }

    @Inject
    private PessoaService pessoaService;

    private Pessoa avoMasculino;
    private Pessoa avoFeminino;
    private Pessoa pai;
    private Pessoa mae;

    @Before
    public void prepatarTeste() {

	avoMasculino = new PessoaDataBuilder().nomeCompleto("Avo Masculino").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("000.000.000-00").pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	avoFeminino = new PessoaDataBuilder().nomeCompleto("Avo Feminino").sexo(GeneroEnum.FEMININO).dataNascimento(new Date()).cpf("111.111.111-11").pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	pai = new PessoaDataBuilder().nomeCompleto("Pai").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("222.222.222-22").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	mae = new PessoaDataBuilder().nomeCompleto("Mae").sexo(GeneroEnum.FEMININO).dataNascimento(new Date()).cpf("333.333.333-33").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

    }

    @Deployment
    public static JavaArchive deploy() {

	return createDeployment().addClass(PessoaDataBuilder.class).addClass(NegocioException.class).addClass(PessoaService.class).addClass(FiltroListagemPessoasDTO.class).addClass(PessoaServiceEJB.class).addClass(AppConfig.class).addClass(Pessoa.class).addClass(GeneroEnum.class).addClass(IdentificadorQueries.class).addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml").addAsResource("META-INF/queries.xml", "META-INF/queries.xml").addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Test
    public void deveInserirPessoasComCpfsDiferentes() {

	Pessoa pessoa1 = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("999.999.999-99").pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa pessoa2 = new PessoaDataBuilder().nomeCompleto("Pessoa2").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("888.888.888-88").pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	try {

	    pessoaService.inserir(pessoa1);
	    pessoaService.inserir(pessoa2);

	} catch (Exception e) {

	    Assert.fail("Não foi possível inserir duas pessoas com CPFS diferentes");

	}

    }

    @Test
    public void naoDeveInserirPessoasComCpfsIguais() {

	String cpf = "999.999.999-99";
	Pessoa pessoa1 = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf(cpf).pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa pessoa2 = new PessoaDataBuilder().nomeCompleto("Pessoa2").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf(cpf).pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	try {

	    pessoaService.inserir(pessoa1);
	    pessoaService.inserir(pessoa2);

	} catch (Exception e) {

	    Assert.assertEquals(e.getCause(), new NegocioException(IdentificadorNegocioExceptions.PESSOAS_COM_CPFS_IGUAIS));
	    return;

	}

	Assert.fail("Permitiu que pessoas com CPFs iguais fossem inseridas");

    }

    @Test
    public void naoDevePermitirQueOPaiSejaIgualOPaiDaMae() {

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);
	pessoaService.inserir(pai);
	pessoaService.inserir(mae);

	Pessoa irmao1 = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.FEMININO).dataNascimento(new Date()).cpf("444.444.444-44").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa irmao2 = new PessoaDataBuilder().nomeCompleto("Pessoa2").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("555.555.555-55").pai(avoMasculino).mae(mae).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	Boolean permitiuAInclusaoDePaisIrmaosDaPessoa = false;

	try {

	    pessoaService.inserir(irmao1);
	    pessoaService.inserir(irmao2);

	} catch (Exception e) {

	    Assert.assertEquals(e.getCause(), new NegocioException(IdentificadorNegocioExceptions.PAIS_SAO_IRMAOS));
	    return;

	}

	Assert.assertFalse("Permitiu que pessoas tivessem irmaos como pais", permitiuAInclusaoDePaisIrmaosDaPessoa);

    }

    @Test
    public void naoDevePermitirQueAMaeSejaIgualAMaeDoPai() {

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);
	pessoaService.inserir(pai);
	pessoaService.inserir(mae);

	Pessoa irmao1 = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.FEMININO).dataNascimento(new Date()).cpf("444.444.444-44").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa irmao2 = new PessoaDataBuilder().nomeCompleto("Pessoa2").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("555.555.555-55").pai(pai).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	Boolean permitiuAInclusaoDePaisIrmaosDaPessoa = false;

	try {

	    pessoaService.inserir(irmao1);
	    pessoaService.inserir(irmao2);

	} catch (Exception e) {

	    Assert.assertEquals(e.getCause(), new NegocioException(IdentificadorNegocioExceptions.PAIS_SAO_IRMAOS));
	    return;

	}

	Assert.assertFalse("Permitiu que pessoas tivessem irmaos como pais", permitiuAInclusaoDePaisIrmaosDaPessoa);

    }

    @Test
    public void naoDevePermitirQueAMaeSejaIgualOPaiDoPai() {

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);
	pessoaService.inserir(pai);
	pessoaService.inserir(mae);

	Pessoa irmao1 = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.FEMININO).dataNascimento(new Date()).cpf("444.444.444-44").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa irmao2 = new PessoaDataBuilder().nomeCompleto("Pessoa2").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("555.555.555-55").pai(pai).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	Boolean permitiuAInclusaoDePaisIrmaosDaPessoa = false;

	try {

	    pessoaService.inserir(irmao1);
	    pessoaService.inserir(irmao2);

	} catch (Exception e) {

	    Assert.assertEquals(e.getCause(), new NegocioException(IdentificadorNegocioExceptions.PAIS_SAO_IRMAOS));
	    return;

	}

	Assert.assertFalse("Permitiu que pessoas tivessem irmaos como pais", permitiuAInclusaoDePaisIrmaosDaPessoa);

    }

    @Test
    public void devePermitirFilhosComPaiOuMaeNulosExclusivamente() {

	pai.setMae(null);
	mae.setPai(null);

	try {

	    pessoaService.inserir(avoMasculino);
	    pessoaService.inserir(avoFeminino);
	    pessoaService.inserir(pai);
	    pessoaService.inserir(mae);

	} catch (Exception e) {

	    Assert.fail("Nao perimitiiu que filhos com pais ou mae nulos exclusivamente fossem incluidos");

	}

    }

    @Test
    public void deveDeveIdentificarQuePessoaPossuiFilhos() {

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);
	pessoaService.inserir(pai);

	Assert.assertTrue("Nao identificou que o avo masculino possui filhos", pessoaService.possuiFilhos(avoMasculino.getId()));
	Assert.assertTrue("Nao identificou que o avo feminino possui filhos", pessoaService.possuiFilhos(avoFeminino.getId()));

    }

    @Test
    public void naoDeveDeveIdentificarQuePessoaPossuiFilhos() {

	pessoaService.inserir(avoMasculino);

	Assert.assertFalse("Nao identificou que o avo masculino possui filhos", pessoaService.possuiFilhos(avoMasculino.getId()));

    }

    @Test
    public void deveExcluirPessoa() {

	pessoaService.inserir(avoMasculino);
	Long idPessoaIncluida = avoMasculino.getId();

	Assert.assertNotNull("Nao incluiu a pessa antes da exclusao", entityManager.find(Pessoa.class, idPessoaIncluida));

	pessoaService.excluir(avoMasculino);

	Assert.assertNull("Nao excluiu a pessoa", entityManager.find(Pessoa.class, idPessoaIncluida));

    }

    @Test
    public void deveExcluirAPessoaEOsFilhosDelaRecursivamente() {

	Pessoa neto = new PessoaDataBuilder().nomeCompleto("Pessoa1").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("999.999.999-99").pai(null).mae(null).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	neto.setPai(pai);

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);
	pessoaService.inserir(pai);
	pessoaService.inserir(neto);

	Long idAvoMasculino = avoMasculino.getId();
	Long idAvoFeminino = avoFeminino.getId();
	Long idPai = pai.getId();
	Long idNeto = neto.getId();

	Assert.assertNotNull("Nao incluiu o avo feminino antes da exclusao", entityManager.find(Pessoa.class, idAvoFeminino));
	Assert.assertNotNull("Nao incluiu o avo masculino da exclusao", entityManager.find(Pessoa.class, idAvoMasculino));
	Assert.assertNotNull("Nao incluiu o pai da exclusao", entityManager.find(Pessoa.class, idPai));
	Assert.assertNotNull("Nao incluiu o neto da exclusao", entityManager.find(Pessoa.class, idNeto));

	pessoaService.excluir(avoMasculino);

	Assert.assertNull("Nao excluiu o avo", entityManager.find(Pessoa.class, idAvoMasculino));
	Assert.assertNull("Nao excluiu o pai", entityManager.find(Pessoa.class, idPai));
	Assert.assertNull("Nao excluiu o neto", entityManager.find(Pessoa.class, idNeto));

    }

    private Calendar construirCalendarParaOsTestesDeListagemDePessoas() {
	Calendar calendarDataNascimento = Calendar.getInstance();
	calendarDataNascimento.set(Calendar.DAY_OF_MONTH, 1);
	calendarDataNascimento.set(Calendar.MONTH, Calendar.JANUARY);
	return calendarDataNascimento;
    }

    private void gerarMassaDadosTestesListagem() {
	Calendar calendarDataNascimento = construirCalendarParaOsTestesDeListagemDePessoas();
	Integer anoDoPrimeiroNascimento = 1985;

	pessoaService.inserir(avoMasculino);
	pessoaService.inserir(avoFeminino);

	for (int numeroDePessoas = 0; numeroDePessoas < 25; numeroDePessoas++) {
	    calendarDataNascimento.set(Calendar.YEAR, anoDoPrimeiroNascimento + numeroDePessoas);
	    Pessoa pessoa = new PessoaDataBuilder().nomeCompleto("Pessoa" + numeroDePessoas).sexo(GeneroEnum.MASCULINO).dataNascimento(calendarDataNascimento.getTime()).cpf("" + numeroDePessoas).pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	    pessoaService.inserir(pessoa);
	}

    }

    @Test
    public void deveContarPessoasCorretamenteSemFiltroPreenchido() {
	gerarMassaDadosTestesListagem();
	Assert.assertEquals("Nao contou os registros corretamente", new Long(27L), pessoaService.contaTotalPessoas(new FiltroListagemPessoasDTO()));
    }

    @Test
    public void deveListarPessoasCorretamenteSemFiltroPreenchido() {
	Integer quantidadeDeRegistrosPorPagina = 10;
	gerarMassaDadosTestesListagem();
	FiltroListagemPessoasDTO filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();

	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> primeiraPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("O tamanho da primeira pagina nao esta correto", new Long(quantidadeDeRegistrosPorPagina), new Long(primeiraPagina.size()));
	Assert.assertEquals("O primeiro registro da primeira pagina nao foi o esperado", "Avo Masculino", primeiraPagina.get(0).getNomeCompleto());
	Assert.assertEquals("O ultimo registro da primeira pagina nao foi o esperado", "Pessoa7", primeiraPagina.get(9).getNomeCompleto());

	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> segundaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("O tamanho da segunda pagina nao esta correto", new Long(quantidadeDeRegistrosPorPagina), new Long(segundaPagina.size()));
	Assert.assertEquals("O primeiro registro da segunda pagina nao foi o esperado", "Pessoa8", segundaPagina.get(0).getNomeCompleto());
	Assert.assertEquals("O ultimo registro da segunda pagina nao foi o esperado", "Pessoa17", segundaPagina.get(9).getNomeCompleto());

	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(quantidadeDeRegistrosPorPagina * 2);
	List<Pessoa> terceiraPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("O tamanho da terceira pagina nao esta correto", new Long(7), new Long(terceiraPagina.size()));
	Assert.assertEquals("O primeiro registro da terceira pagina nao foi o esperado", "Pessoa18", terceiraPagina.get(0).getNomeCompleto());
	Assert.assertEquals("O ultimo registro da terceira pagina nao foi o esperado", "Pessoa24", terceiraPagina.get(6).getNomeCompleto());

    }

    @Test
    public void deveContarEListarPessoasCorretamenteQuandoAsDatasForemInformadas() {
	Integer quantidadeDeRegistrosPorPagina = 10;
	gerarMassaDadosTestesListagem();
	Calendar calendarDataNascimento = construirCalendarParaOsTestesDeListagemDePessoas();
	calendarDataNascimento.set(Calendar.YEAR, 1990);
	FiltroListagemPessoasDTO filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	filtroListagemPessoasDTO.setDataInicialAniversario(calendarDataNascimento.getTime());
	calendarDataNascimento.set(Calendar.YEAR, 1995);
	filtroListagemPessoasDTO.setDataFinalAniversario(calendarDataNascimento.getTime());
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> unicaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao retornou uma pagina com o numero correto de pessoas", new Long(5), new Long(unicaPagina.size()));
	Long numeroTotalDeRegistros = pessoaService.contaTotalPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao contou o total de registros corretamente", new Long(5), new Long(numeroTotalDeRegistros));

    }

    @Test
    public void deveContarEListarPessoasCorretamenteQuandoOCpfEInformado() {
	Integer quantidadeDeRegistrosPorPagina = 10;
	gerarMassaDadosTestesListagem();
	FiltroListagemPessoasDTO filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	filtroListagemPessoasDTO.setCpf("000.000.000-00");
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> unicaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao retornou uma pagina com o numero correto de pessoas", new Long(1), new Long(unicaPagina.size()));
	Long numeroTotalDeRegistros = pessoaService.contaTotalPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao contou o total de registros corretamente", new Long(1), new Long(numeroTotalDeRegistros));

    }

    @Test
    public void deveContarEListarPessoasCorretamenteQuandoONomeDosPaisEInformado() {

	gerarMassaDadosTestesListagem();
	Pessoa paiComONomeDiferente = new PessoaDataBuilder().nomeCompleto("paiComONomeDiferente").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("888.888.888-88").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa maeComONomeDiferente = new PessoaDataBuilder().nomeCompleto("maeComONomeDiferente").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("555.555.555-55").pai(avoMasculino).mae(avoFeminino).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();
	Pessoa pessoaComPaisDiferentes = new PessoaDataBuilder().nomeCompleto("voMasculinoComONomeDiferente").sexo(GeneroEnum.MASCULINO).dataNascimento(new Date()).cpf("666.666.666-66").pai(paiComONomeDiferente).mae(maeComONomeDiferente).municipioNascimento("Municipio").ufNascimento("SP").dddTelefoneCelular("99").prefixoTelefoneCelular("9999").sufixoTelefoneCelular("9999").dddTelefoneFixo("99").prefixoTelefoneFixo("9999").sufixoTelefoneFixo("9999").getPessoa();

	pessoaService.inserir(paiComONomeDiferente);
	pessoaService.inserir(maeComONomeDiferente);
	pessoaService.inserir(pessoaComPaisDiferentes);

	Integer quantidadeDeRegistrosPorPagina = 10;
	FiltroListagemPessoasDTO filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	filtroListagemPessoasDTO.setNomeMaeOuPai("paiComONomeDiferente");
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> unicaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao retornou uma pagina com o numero correto de pessoas quando o nome do pai foi informado", new Long(1), new Long(unicaPagina.size()));
	Long numeroTotalDeRegistros = pessoaService.contaTotalPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao contou o total de registros corretamente quando o nome do pai foi informado", new Long(1), new Long(numeroTotalDeRegistros));

	
	filtroListagemPessoasDTO.setNomeMaeOuPai("maeComONomeDiferente");
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	unicaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao retornou uma pagina com o numero correto de pessoas quando o nome da mae foi informado", new Long(1), new Long(unicaPagina.size()));
	numeroTotalDeRegistros = pessoaService.contaTotalPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao contou o total de registros corretamente quando o nome da mae foi informado", new Long(1), new Long(numeroTotalDeRegistros));

    }

    @Test
    public void deveContarEListarPessoasCorretamenteQuandoONomeEInformado() {
	gerarMassaDadosTestesListagem();
	Integer quantidadeDeRegistrosPorPagina = 10;
	FiltroListagemPessoasDTO filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	filtroListagemPessoasDTO.setNomePessoa("Pessoa24");;
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(quantidadeDeRegistrosPorPagina);
	List<Pessoa> unicaPagina = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao retornou uma pagina com o numero correto de pessoas quando o nome  foi informado", new Long(1), new Long(unicaPagina.size()));
	Long numeroTotalDeRegistros = pessoaService.contaTotalPessoas(filtroListagemPessoasDTO);
	Assert.assertEquals("Nao contou o total de registros corretamente quando o nome  foi informado", new Long(1), new Long(numeroTotalDeRegistros));
    }

}