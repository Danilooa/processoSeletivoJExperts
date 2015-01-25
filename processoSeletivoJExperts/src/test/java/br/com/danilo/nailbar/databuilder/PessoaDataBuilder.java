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

    public PessoaDataBuilder telefoneCelular(String telefoneCelular) {
	this.pessoa.setTelefoneCelular(telefoneCelular);
	return this;
    }

    public PessoaDataBuilder telefoneFixo(String telefoneFixo) {
	this.pessoa.setTelefoneFixo(telefoneFixo);
	return this;
    }

    public Pessoa getPessoa() {
	return pessoa;
    }
}
