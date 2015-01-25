package br.com.jexperts.danilooa.crudpessoas.jsf.comparators;

import java.util.InputMismatchException;
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

@FacesValidator("cpfValidator")
public class CpfValidator implements Validator {

    private static final String CPF_PATTERN = "^\\d\\d\\d\\.\\d\\d\\d\\.\\d\\d\\d-\\d\\d$";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
	if (value == null || value.toString().trim().isEmpty()) {
	    return;
	}
	
	if( isCPF(value.toString())){
	    return;
	}
	
	FacesMessage msg = new FacesMessage(JavaServerFacesUtils.getMessage(Mensagens.CPF_INVALIDO.name()), null);
	msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	throw new ValidatorException(msg);

    }

    private Pattern pattern;
    private Matcher matcher;

    public CpfValidator() {
	pattern = Pattern.compile(CPF_PATTERN);
    }

    private boolean isCPF(String CPF) {
	matcher = pattern.matcher(CPF);
	if (!matcher.matches()) {
	    return false;
	}
	CPF = CPF.replaceAll("\\D", "");
	if (CPF.equals("000.000.000-00") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11))
	    return (false);
	char dig10, dig11;
	int sm, i, r, num, peso;

	try {
	    sm = 0;
	    peso = 10;
	    for (i = 0; i < 9; i++) {

		num = (int) (CPF.charAt(i) - 48);
		sm = sm + (num * peso);
		peso = peso - 1;
	    }
	    r = 11 - (sm % 11);
	    if ((r == 10) || (r == 11))
		dig10 = '0';
	    else
		dig10 = (char) (r + 48);

	    sm = 0;
	    peso = 11;
	    for (i = 0; i < 10; i++) {
		num = (int) (CPF.charAt(i) - 48);
		sm = sm + (num * peso);
		peso = peso - 1;
	    }
	    r = 11 - (sm % 11);
	    if ((r == 10) || (r == 11))
		dig11 = '0';
	    else
		dig11 = (char) (r + 48);

	    if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
		return (true);
	    else
		return (false);
	} catch (InputMismatchException erro) {
	    return (false);
	}
    }
}
