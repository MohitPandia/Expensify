package com.expensify.expensify.Exception.User;

public class UserNotFound extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public UserNotFound() {

	}

	public UserNotFound(String str) {
		super(str);
		this.message = str;
	}
}
