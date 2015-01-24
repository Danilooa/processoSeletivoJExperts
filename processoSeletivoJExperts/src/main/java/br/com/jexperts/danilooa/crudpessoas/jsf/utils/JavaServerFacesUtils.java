package br.com.jexperts.danilooa.crudpessoas.jsf.utils;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JavaServerFacesUtils {

    public static String getMessage(String chaveMensagem) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msgs");
	return bundle.getObject(chaveMensagem).toString();
    }

    public static void adicionarMensagem(String chaveMensagem) {
	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMessage(chaveMensagem)));
    }

}
