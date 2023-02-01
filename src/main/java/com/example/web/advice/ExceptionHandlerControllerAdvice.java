package com.example.web.advice;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.exception.ApplicationException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ApplicationException.class)
	public String handleApplicationException(ApplicationException ex) {
		ex.printStackTrace();
		return "error/app";
	}
	
	@ExceptionHandler(DataAccessException.class)
	public String handleDataAccessException(DataAccessException ex) {
		ex.printStackTrace();
		return "error/db";
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException ex) {
		ex.printStackTrace();
		return "error/unknown";
	}
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex) {
		ex.printStackTrace();
		return "error/unknown";
	}
}
