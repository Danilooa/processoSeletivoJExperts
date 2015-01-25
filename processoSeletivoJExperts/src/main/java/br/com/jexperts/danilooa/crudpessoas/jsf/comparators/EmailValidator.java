package br.com.jexperts.danilooa.crudpessoas.jsf.comparators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.jexperts.danilooa.crudpessoas.enums.Mensagens;
import br.com.jexperts.danilooa.crudpessoas.jsf.utils.JavaServerFacesUtils;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private Pattern pattern;
    private Matcher matcher;

    public EmailValidator() {
	pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	if(value == null || value.toString().trim().isEmpty()){
	    return;
	}
	matcher = pattern.matcher(value.toString());
	if (matcher.matches()) {
	    return;
	}
	FacesMessage msg = new FacesMessage(JavaServerFacesUtils.getMessage(Mensagens.EMAIL_INVALIDO.name()), null);
	msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	throw new ValidatorException(msg);

    }
}
