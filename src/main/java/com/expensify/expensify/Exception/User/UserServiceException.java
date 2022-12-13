package com.expensify.expensify.Exception.User;

public class UserServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public UserServiceException() {

	}

	public UserServiceException(String str) {
		super(str);
		this.message = str;
	}
}
