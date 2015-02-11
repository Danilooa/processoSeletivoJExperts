package br.com.jexperts.danilooa.crudpessoas.datamodel;

import java.util.List;

import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;

public class PessoaPerfilDataModel {

    private Integer indexPessoaCorrente;
    private List<Pessoa> listPessoas;
    private Pessoa pessoaCorrente;

    public PessoaPerfilDataModel(Integer indexPessoaCorrente, List<Pessoa> listPessoas) {
	this.indexPessoaCorrente = indexPessoaCorrente;
	pessoaCorrente = listPessoas.get(indexPessoaCorrente);
	this.listPessoas = listPessoas;
    }

    public Boolean deveHabilitarBotaoAvancar() {
	if (indexPessoaCorrente + 1 >= listPessoas.size()) {
	    return false;
	}
	return true;
    }

    public Boolean deveHabilitarBotaoRetroceder() {
	return indexPessoaCorrente > 0;
    }

    public void avancar(){
	indexPessoaCorrente++;
	pessoaCorrente = listPessoas.get(indexPessoaCorrente);
    }
    
    public void retroceder(){
	indexPessoaCorrente--;
	pessoaCorrente = listPessoas.get(indexPessoaCorrente);
    }
    
    public Integer getIndexPessoaCorrente() {
	return indexPessoaCorrente;
    }

    public Pessoa getPessoaCorrente(){
	return pessoaCorrente;
    }

    public void setPessoaCorrente(Pessoa pessoaCorrente) {
        this.pessoaCorrente = pessoaCorrente;
    }
    
}
