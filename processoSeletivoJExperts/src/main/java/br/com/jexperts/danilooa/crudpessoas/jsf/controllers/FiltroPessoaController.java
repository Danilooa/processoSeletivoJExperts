package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.com.jexperts.danilooa.crudpessoas.datamodel.PessoaPerfilDataModel;
import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.enums.Mensagens;
import br.com.jexperts.danilooa.crudpessoas.jsf.datamodels.PessoaLazyDataModel;
import br.com.jexperts.danilooa.crudpessoas.jsf.utils.JavaServerFacesUtils;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;

@ManagedBean
@ViewScoped
public class FiltroPessoaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PessoaService pessoaService;

    private PessoaLazyDataModel lazyModel;
    private FiltroListagemPessoasDTO filtroListagemPessoasDTO;

    private Pessoa pessoaSelecionada;
    
    private DataTable dataTable;

    private PessoaPerfilDataModel pessoaPerfilDataModel = null;
    
    @PostConstruct
    public void init() {
	filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	lazyModel = new PessoaLazyDataModel(pessoaService, filtroListagemPessoasDTO);
	filtroListagemPessoasDTO = filtroListagemPessoasDTO.clone();
    }

    public LazyDataModel<Pessoa> getLazyModel() {
	return lazyModel;
    }

    public FiltroListagemPessoasDTO getFiltroListagemPessoasDTO() {
	return filtroListagemPessoasDTO;
    }

    public void setFiltroListagemPessoasDTO(FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
	this.filtroListagemPessoasDTO = filtroListagemPessoasDTO;
    }

    public void setLazyModel(PessoaLazyDataModel lazyModel) {
	this.lazyModel = lazyModel;
    }

    public void onRowSelect(SelectEvent event) {
	pessoaSelecionada = (Pessoa) event.getObject();
    }

    public void selecionarPessoa(Pessoa pessoa){
	this.pessoaSelecionada = pessoa;
    }
    
    public void exibirPerfil(Pessoa pessoa){
	Integer indexPessoaCorrente = lazyModel.getPessoasDaPaginaCorrente().indexOf(pessoa);
	this.pessoaPerfilDataModel = new PessoaPerfilDataModel(indexPessoaCorrente, lazyModel.getPessoasDaPaginaCorrente());
    }
    
    public void listar(){
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	lazyModel = new PessoaLazyDataModel(pessoaService, filtroListagemPessoasDTO);
	filtroListagemPessoasDTO = filtroListagemPessoasDTO.clone();
	dataTable.reset();	
    }
    
    public void excluir() {
	pessoaService.excluir(pessoaSelecionada);
	JavaServerFacesUtils.adicionarMensagemInformacao(Mensagens.EXCLUSAO_REALIZADA_COM_SUCESSO.name());
	lazyModel.setRowCount(lazyModel.getRowCount() - 1);
    }

    public String getMensagemDelecao() {
	if(pessoaSelecionada == null){
	    return "";
	}
	
	if(pessoaService.possuiFilhos(pessoaSelecionada.getId())){
	    return JavaServerFacesUtils.getMessage(Mensagens.PESSOA_POSSUI_FILHOS_QUE_SERAO_EXCLUIDOS_RECURSIVAMENTE.name());
	}
	return JavaServerFacesUtils.getMessage(Mensagens.DESEJA_EXCLUIR_PESSOA.name());
    }

    public String getMessagemConfirmacaoExclusao() {
	if (pessoaSelecionada == null) {
	    return "";
	}
	if (pessoaService.possuiFilhos(pessoaSelecionada.getId())) {
	    return Mensagens.PESSOA_POSSUI_FILHOS_QUE_SERAO_EXCLUIDOS_RECURSIVAMENTE.name();
	}
	return Mensagens.DESEJA_EXCLUIR_PESSOA.name();
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public PessoaPerfilDataModel getPessoaPerfilDataModel() {
        return pessoaPerfilDataModel;
    }

    public void setPessoaPerfilDataModel(PessoaPerfilDataModel pessoaPerfilDataModel) {
        this.pessoaPerfilDataModel = pessoaPerfilDataModel;
    }
    
}
