package br.com.jexperts.danilooa.crudpessoas.jsf.controllers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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

    public GeneroEnum[] getGeneros() {
	return GeneroEnum.values();
    }

    public String cadastrar() {
	String outcome = "pessoas";
	String chaveMensagem = Mensagens.ALTERACAO_REALIZADA_COM_SUCESSO.name();

	if (pessoa.getId() == null) {
	    chaveMensagem = Mensagens.INCLUSAO_REALIZADA_COM_SUCESSO.name();
	}

	try {
	    pessoaService.inserir(pessoa);
	    JavaServerFacesUtils.adicionarMensagemInformacao(chaveMensagem);
	} catch (EJBException e) {
	    outcome = null;
	    JavaServerFacesUtils.tratarEJBException(e);
	}
	return outcome;
    }

    public void tratarUploadImagem(FileUploadEvent event) throws IOException {
	byte[] imagem = new byte[(int) event.getFile().getSize()];
	pessoa.setImagem(imagem);
	DataInputStream dataInputStream = new DataInputStream(event.getFile().getInputstream());
	dataInputStream.readFully(imagem);
	JavaServerFacesUtils.adicionarMensagemInformacao(Mensagens.IMAGEM_ATUALIZADA_COM_SUCESSO.name());
    }

    public StreamedContent getImagem() {
	getPessoa();
	InputStream inputStreaImagem = null;
	if (pessoa.getImagem() == null) {
	    inputStreaImagem = Thread.currentThread().getContextClassLoader().getResourceAsStream("imagemNaoCadastrada.jpg");
	}
	if (pessoa.getImagem() != null) {
	    inputStreaImagem = new ByteArrayInputStream(pessoa.getImagem());
	}
	DefaultStreamedContent defaultStreamedContent = new DefaultStreamedContent(inputStreaImagem , "image/png");
	return defaultStreamedContent;
    }

    public Long getIdPessoa() {
	return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
	this.idPessoa = idPessoa;
    }

    public Pessoa getPessoa() {
	if (pessoa != null) {
	    return pessoa;
	}
	if (idPessoa != null) {
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
