package br.com.jexperts.danilooa.crudpessoas.enums;

public enum GeneroEnum {

    FEMININO("F"), 
    MASCULINO("M");

    private String simbolo;

    GeneroEnum(String simbolo) {
	this.simbolo = simbolo;
    }

    public String getSimbolo() {
	return simbolo;
    }

}
