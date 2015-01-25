package br.com.jexperts.danilooa.crudpessoas.jsf.utils;

import java.util.ResourceBundle;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException;

public class JavaServerFacesUtils {
    
    public final static String CHAVE_MENSAGEM_PARA_EXIBIR_GROWL = "exibirGrowl";

    public static String getMessage(String chaveMensagem) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "msgs");
	return bundle.getObject(chaveMensagem).toString();
    }

    public static void adicionarMensagem(Severity severidade, String chaveMensagem) {
	FacesContext.getCurrentInstance().addMessage(CHAVE_MENSAGEM_PARA_EXIBIR_GROWL, new FacesMessage(severidade, getMessage(chaveMensagem), ""));
    }

    public static void adicionarMensagemInformacao(String chaveMensagem) {
	adicionarMensagem(FacesMessage.SEVERITY_INFO, chaveMensagem);
    }

    public static void adicionarMensagemErro(String chaveMensagem) {
	adicionarMensagem(FacesMessage.SEVERITY_ERROR, chaveMensagem);
    }

    public static void tratarEJBException(EJBException e) {

	if (e.getCause() == null) {
	    throw e;
	}
	
	if(!(e.getCause() instanceof NegocioException)){
	    throw e;
	}

	NegocioException causa = (NegocioException)e.getCause();
	adicionarMensagemErro( causa.getIdentificadoresBusinessException().name());
    }
}
