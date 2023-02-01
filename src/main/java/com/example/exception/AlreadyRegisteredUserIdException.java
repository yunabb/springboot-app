package com.example.exception;

public class AlreadyRegisteredUserIdException extends ApplicationException{

	public AlreadyRegisteredUserIdException(String message) {
		super(message);
	}
}
