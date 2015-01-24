package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

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

    private LazyDataModel<Pessoa> lazyModel;
    private FiltroListagemPessoasDTO filtroListagemPessoasDTO;

    private Pessoa pessoaSelecionada;
    
    private DataTable dataTable;

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

    public void setLazyModel(LazyDataModel<Pessoa> lazyModel) {
	this.lazyModel = lazyModel;
    }

    public void onRowSelect(SelectEvent event) {
	pessoaSelecionada = (Pessoa) event.getObject();
    }

    public void selecionarPessoa(Pessoa pessoa){
	this.pessoaSelecionada = pessoa;
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

    public String pessoaPossuiFilhos(Pessoa pessoa) {
	return pessoaService.possuiFilhos(pessoa.getId()).toString();
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
    
    
}
