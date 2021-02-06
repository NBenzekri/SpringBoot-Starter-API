package com.nbenz.sbootstarter.exception;

public class NbenzException extends RuntimeException {
	
	private NbenzError error;
	
	public NbenzException(NbenzError nbenzError){
		super(nbenzError.getDescription());
		this.error = nbenzError;
	}

	public NbenzError getError() {
		return error;
	}

	public void setError(NbenzError error) {
		this.error = error;
	}
}