package com.example.exception;

public class AlreadyRegisteredEmailException extends ApplicationException {
	
	private static final long serialVersionUID = -922082434086720004L;

	public AlreadyRegisteredEmailException(String message) {
		super(message);
	}
}
