package br.com.jexperts.danilooa.crudpessoas.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    private byte[] imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPai", referencedColumnName="id")
    private Pessoa pai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idMae", referencedColumnName="id")
    private Pessoa mae;

    private String municipioNascimento;
    private String ufNascimento;

    private String dddTelefoneCelular;
    private String prefixoTelefoneCelular;
    private String sufixoTelefoneCelular;

    private String dddTelefoneFixo;
    private String prefixoTelefoneFixo;
    private String sufixoTelefoneFixo;

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

    public String getDddTelefoneCelular() {
	return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(String dddTelefoneCelular) {
	this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public String getPrefixoTelefoneCelular() {
	return prefixoTelefoneCelular;
    }

    public void setPrefixoTelefoneCelular(String prefixoTelefoneCelular) {
	this.prefixoTelefoneCelular = prefixoTelefoneCelular;
    }

    public String getSufixoTelefoneCelular() {
	return sufixoTelefoneCelular;
    }

    public void setSufixoTelefoneCelular(String sufixoTelefoneCelular) {
	this.sufixoTelefoneCelular = sufixoTelefoneCelular;
    }

    public String getDddTelefoneFixo() {
	return dddTelefoneFixo;
    }

    public void setDddTelefoneFixo(String dddTelefoneFixo) {
	this.dddTelefoneFixo = dddTelefoneFixo;
    }

    public String getPrefixoTelefoneFixo() {
	return prefixoTelefoneFixo;
    }

    public void setPrefixoTelefoneFixo(String prefixoTelefoneFixo) {
	this.prefixoTelefoneFixo = prefixoTelefoneFixo;
    }

    public String getSufixoTelefoneFixo() {
	return sufixoTelefoneFixo;
    }

    public void setSufixoTelefoneFixo(String sufixoTelefoneFixo) {
	this.sufixoTelefoneFixo = sufixoTelefoneFixo;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    public String telefoneCelularFormatado(){
	StringBuilder telefoneCelularFormato = new StringBuilder();
	if(dddTelefoneCelular == null || dddTelefoneCelular.isEmpty()){
	    return "";
	}
	if(prefixoTelefoneCelular == null || prefixoTelefoneCelular.isEmpty()){
	    return "";
	}
	if(sufixoTelefoneCelular == null || sufixoTelefoneCelular.isEmpty()){
	    return "";
	}
	telefoneCelularFormato.append(dddTelefoneCelular);
	telefoneCelularFormato.append("-");
	telefoneCelularFormato.append(prefixoTelefoneCelular);
	telefoneCelularFormato.append("-");
	telefoneCelularFormato.append(sufixoTelefoneCelular);
	return telefoneCelularFormato.toString();
    }
    
    
    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
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
