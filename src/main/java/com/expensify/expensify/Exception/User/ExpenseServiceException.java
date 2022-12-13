package com.expensify.expensify.Exception.User;

public class ExpenseServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public ExpenseServiceException() {

	}

	public ExpenseServiceException(String str) {
		super(str);
		this.message = str;
	}
}
