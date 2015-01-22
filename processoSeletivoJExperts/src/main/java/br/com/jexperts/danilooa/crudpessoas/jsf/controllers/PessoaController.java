package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;


@ManagedBean
@ViewScoped
public class PessoaController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EJB
    private PessoaService pessoaService;

    @PostConstruct
    public void init() {
    }

}
