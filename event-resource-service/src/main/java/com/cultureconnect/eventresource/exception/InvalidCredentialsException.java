package com.cultureconnect.eventresource.exception;

@SuppressWarnings("serial")
public class InvalidCredentialsException extends RuntimeException{

	public InvalidCredentialsException(String message) {
		super(message);
	}
}
