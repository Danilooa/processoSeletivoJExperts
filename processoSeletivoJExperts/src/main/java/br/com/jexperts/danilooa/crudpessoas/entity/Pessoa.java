package br.com.jexperts.danilooa.crudpessoas.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import br.com.jexperts.danilooa.crudpessoas.enums.GeneroEnum;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private GeneroEnum genero;
    private Date dataNascimento;
    private String cpf;
    private String email;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPai", referencedColumnName="id")
    private Pessoa pai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMae", referencedColumnName="id")
    private Pessoa mae;

    private String municipioNascimento;
    private String ufNascimento;

    private String telefoneCelular;

    private String telefoneFixo;

    public String getNomeCompleto() {
	return nomeCompleto;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setNomeCompleto(String nomeCompleto) {
	this.nomeCompleto = nomeCompleto;
    }

    public GeneroEnum getGenero() {
	return genero;
    }

    public void setGenero(GeneroEnum sexo) {
	this.genero = sexo;
    }

    public Date getDataNascimento() {
	return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
	this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
	return cpf;
    }

    public void setCpf(String cpf) {
	this.cpf = cpf;
    }

    public Pessoa getPai() {
	return pai;
    }

    public void setPai(Pessoa pai) {
	this.pai = pai;
    }

    public Pessoa getMae() {
	return mae;
    }

    public void setMae(Pessoa mae) {
	this.mae = mae;
    }

    public String getMunicipioNascimento() {
	return municipioNascimento;
    }

    public void setMunicipioNascimento(String municipioNascimento) {
	this.municipioNascimento = municipioNascimento;
    }

    public String getUfNascimento() {
	return ufNascimento;
    }

    public void setUfNascimento(String ufNascimento) {
	this.ufNascimento = ufNascimento;
    }
    
    public byte[] getImagem() {
        return imagem;
    }

    public String getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getTelefoneFixo() {
        return telefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        this.telefoneFixo = telefoneFixo;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (id == null) {
	    return false;
	}

	if (!(obj instanceof Pessoa)) {
	    return false;
	}

	Pessoa outro = (Pessoa) obj;

	if (outro.id == null) {
	    return false;
	}

	if (!id.equals(outro.id)) {
	    return false;
	}
	return true;
    }

}
