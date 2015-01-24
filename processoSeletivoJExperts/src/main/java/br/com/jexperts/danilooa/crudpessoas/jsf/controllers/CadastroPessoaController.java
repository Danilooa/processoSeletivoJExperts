package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.enums.GeneroEnum;
import br.com.jexperts.danilooa.crudpessoas.enums.Mensagens;
import br.com.jexperts.danilooa.crudpessoas.jsf.utils.JavaServerFacesUtils;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;

@ManagedBean
@ViewScoped
public class CadastroPessoaController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private PessoaService pessoaService;
    
    @Inject
    private EntityManager entityManager;

    private Pessoa pessoa;

    private Long idPessoa;

    public String getTituloPagina() {
	if (idPessoa == null) {
	    return JavaServerFacesUtils.getMessage(Mensagens.CADASTRO_PESSOA.name());
	}
	return JavaServerFacesUtils.getMessage(Mensagens.ALTERACAO_PESSOA.name());
    }

    public GeneroEnum[] getGeneros(){
	return GeneroEnum.values();
    }
    public String cadastrar(){
	pessoaService.inserir(pessoa);
	return "pessoas";
    }
    
    public Long getIdPessoa() {
	return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
	this.idPessoa = idPessoa;
    }

    public Pessoa getPessoa() {
	if(pessoa != null){
	    return pessoa;
	}
	if(idPessoa != null){
	    pessoa = entityManager.find(Pessoa.class, idPessoa);
	    return pessoa;
	}
	pessoa = new Pessoa();
	return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
	this.pessoa = pessoa;
    }

}
