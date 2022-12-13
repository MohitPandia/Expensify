package com.expensify.expensify.Exception.User;

public class UserNotFoundException extends RuntimeException {
	private String message;

	public UserNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String str) {
		super(str);
		this.message = str;
	}

}
