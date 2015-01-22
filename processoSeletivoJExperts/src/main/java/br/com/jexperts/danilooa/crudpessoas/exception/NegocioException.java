package br.com.jexperts.danilooa.crudpessoas.exception;

public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static enum IdentificadorNegocioExceptions {
	PESSOAS_COM_CPFS_IGUAIS,
	PAIS_SAO_IRMAOS;
    }

    private IdentificadorNegocioExceptions identificadorNegocioExceptions;

    public NegocioException(IdentificadorNegocioExceptions businessExceptionIdentifier) {
	super(businessExceptionIdentifier.toString());
	this.identificadorNegocioExceptions = businessExceptionIdentifier;
    }

    public IdentificadorNegocioExceptions getIdentificadoresBusinessException() {
	return identificadorNegocioExceptions;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((identificadorNegocioExceptions == null) ? 0 : identificadorNegocioExceptions.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	NegocioException other = (NegocioException) obj;
	if (identificadorNegocioExceptions != other.identificadorNegocioExceptions) {
	    return false;
	}
	return true;
    }

}
