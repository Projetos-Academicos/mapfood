package com.codenation.desafiofinal.exception;

public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = -1145687356089503733L;

	public ResourceNotFoundException(String mensagem) {
		super(mensagem);
	}
}
