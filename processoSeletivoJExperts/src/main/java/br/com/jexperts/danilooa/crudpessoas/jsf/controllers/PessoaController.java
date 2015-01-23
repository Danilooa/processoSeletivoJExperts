package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.jsf.datamodels.PessoaLazyDataModel;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;


@ManagedBean
@ViewScoped
public class PessoaController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private PessoaService pessoaService;

    private LazyDataModel<Pessoa> lazyModel;
    private FiltroListagemPessoasDTO filtroListagemPessoasDTO;
    
    private Pessoa pessoaSelecionada;
     
    @PostConstruct
    public void init() {
	filtroListagemPessoasDTO = new FiltroListagemPessoasDTO();
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(0);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(10);
        lazyModel = new PessoaLazyDataModel(pessoaService, filtroListagemPessoasDTO);
    }
 
    public LazyDataModel<Pessoa> getLazyModel() {
        return lazyModel;
    }
 
    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }
 
    public FiltroListagemPessoasDTO getFiltroListagemPessoasDTO() {
        return filtroListagemPessoasDTO;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public void setFiltroListagemPessoasDTO(FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
        this.filtroListagemPessoasDTO = filtroListagemPessoasDTO;
    }

    public void setLazyModel(LazyDataModel<Pessoa> lazyModel) {
        this.lazyModel = lazyModel;
    }

    public void setSelectedCar(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ""+((Pessoa) event.getObject()).getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void filtrarPessoas(){
	System.out.println(filtroListagemPessoasDTO.getCpf());
    }
}
