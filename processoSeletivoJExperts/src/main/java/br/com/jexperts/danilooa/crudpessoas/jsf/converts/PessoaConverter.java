package br.com.jexperts.danilooa.crudpessoas.jsf.converts;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;

@Named
public class PessoaConverter implements Converter {

    @Inject
    private EntityManager entityManager;

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
	if (value == null || value.trim().length() < 1) {
	    return null;
	}
	return entityManager.find(Pessoa.class, Long.parseLong(value));
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
	if (object == null) {
	    return null;
	}
	return String.valueOf(((Pessoa) object).getId());
    }
}
