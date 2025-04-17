package com.spring.boot.keycloack.app.handle;

import org.springframework.http.HttpStatus;

public class KeyCloackException extends Exception {

	private static final long serialVersionUID = 6376559008232800045L;

	private final HttpStatus status;

	public KeyCloackException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	/**
	 * Utilizar para manter a rastreabilidade das exceções - LOG
	 * 
	 * @param message
	 * @param cause
	 */
	public KeyCloackException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}
