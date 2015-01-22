package br.com.jexperts.danilooa.crudpessoas.dto;

import java.util.Date;

public class FiltroListagemPessoasDTO {

    private String nomePessoa;
    private Date dataInicialAniversario;
    private Date dataFinalAniversario;
    private String cpf;
    private String nomeMaeOuPai;
    private Integer quantidadeDeRegistrosPorPagina;
    private Integer indexDoPrimeiraRegistroDaPagina;;

    public String getNomePessoa() {
	return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
	this.nomePessoa = nomePessoa;
    }

    public Date getDataInicialAniversario() {
	return dataInicialAniversario;
    }

    public void setDataInicialAniversario(Date dataInicialAniversario) {
	this.dataInicialAniversario = dataInicialAniversario;
    }

    public Date getDataFinalAniversario() {
	return dataFinalAniversario;
    }

    public void setDataFinalAniversario(Date dataFinalAniversario) {
	this.dataFinalAniversario = dataFinalAniversario;
    }

    public String getCpf() {
	return cpf;
    }

    public void setCpf(String cpf) {
	this.cpf = cpf;
    }

    public String getNomeMaeOuPai() {
	return nomeMaeOuPai;
    }

    public void setNomeMaeOuPai(String nomeMaeOuPai) {
	this.nomeMaeOuPai = nomeMaeOuPai;
    }

    public Integer getQuantidadeDeRegistrosPorPagina() {
	return quantidadeDeRegistrosPorPagina;
    }

    public void setQuantidadeDeRegistrosPorPagina(Integer quantidadeDeRegistrosPorPagina) {
	this.quantidadeDeRegistrosPorPagina = quantidadeDeRegistrosPorPagina;
    }

    public Integer getIndexDoPrimeiraRegistroDaPagina() {
	return indexDoPrimeiraRegistroDaPagina;
    }

    public void setIndexDoPrimeiraRegistroDaPagina(Integer indexDoPrimeiraRegistroDaPagina) {
	this.indexDoPrimeiraRegistroDaPagina = indexDoPrimeiraRegistroDaPagina;
    }

}
