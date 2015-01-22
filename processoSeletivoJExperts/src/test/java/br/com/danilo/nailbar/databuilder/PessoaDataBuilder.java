package br.com.danilo.nailbar.databuilder;

import java.util.Date;

import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.enums.GeneroEnum;

public class PessoaDataBuilder {

    private Pessoa pessoa;

    public PessoaDataBuilder() {
	this.pessoa = new Pessoa();
    }

    public PessoaDataBuilder(Pessoa pessoa) {
	this.pessoa = pessoa;
    }

    public PessoaDataBuilder id(Long id) {
	pessoa.setId(id);
	return this;
    }

    public PessoaDataBuilder nomeCompleto(String nomeCompleto) {
	this.pessoa.setNomeCompleto(nomeCompleto);
	return this;
    }

    public PessoaDataBuilder sexo(GeneroEnum sexo) {
	this.pessoa.setGenero(sexo);
	return this;
    }

    public PessoaDataBuilder dataNascimento(Date dataNascimento) {
	this.pessoa.setDataNascimento(dataNascimento);
	return this;
    }

    public PessoaDataBuilder cpf(String cpf) {
	this.pessoa.setCpf(cpf);
	return this;
    }

    public PessoaDataBuilder pai(Pessoa pai) {
	this.pessoa.setPai(pai);
	return this;
    }

    public PessoaDataBuilder mae(Pessoa mae) {
	this.pessoa.setMae(mae);
	return this;
    }

    public PessoaDataBuilder municipioNascimento(String municipioNascimento) {
	this.pessoa.setMunicipioNascimento(municipioNascimento);
	return this;
    }

    public PessoaDataBuilder ufNascimento(String ufNascimento) {
	this.pessoa.setUfNascimento(ufNascimento);
	return this;
    }

    public PessoaDataBuilder dddTelefoneCelular(String ufNascimento) {
	this.pessoa.setUfNascimento(ufNascimento);
	return this;
    }

    public PessoaDataBuilder prefixoTelefoneCelular(String prefixoTelefoneCelular) {
	this.pessoa.setPrefixoTelefoneCelular(prefixoTelefoneCelular);
	return this;
    }

    public PessoaDataBuilder sufixoTelefoneCelular(String prefixoTelefoneCelular) {
	this.pessoa.setPrefixoTelefoneCelular(prefixoTelefoneCelular);
	return this;
    }

    public PessoaDataBuilder dddTelefoneFixo(String dddTelefoneFixo) {
	this.pessoa.setDddTelefoneFixo(dddTelefoneFixo);
	return this;
    }

    public PessoaDataBuilder prefixoTelefoneFixo(String prefixoTelefoneFixo) {
	this.pessoa.setPrefixoTelefoneFixo(prefixoTelefoneFixo);
	return this;
    }

    public PessoaDataBuilder sufixoTelefoneFixo(String sufixoTelefoneFixo) {
	this.pessoa.setSufixoTelefoneFixo(sufixoTelefoneFixo);
	return this;
    }

    public Pessoa getPessoa() {
	return pessoa;
    }
}
