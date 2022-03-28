package com.execise.estore.estore.exception;

public class CartNotFoundException extends RuntimeException {

	private String message;

	public CartNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
