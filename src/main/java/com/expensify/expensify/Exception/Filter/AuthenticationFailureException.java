package com.expensify.expensify.Exception.Filter;

public class AuthenticationFailureException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public AuthenticationFailureException() {

	}

	public AuthenticationFailureException(String str) {
		super(str);
		this.message = str;
	}
}
