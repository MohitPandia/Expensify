package com.expensify.expensify.entity;

public enum UserRoles {
	USER("USER"), ADMIN("ADMIN");

	private final String expense;

	UserRoles(String s) {
		expense = s;
	}

	@Override
	public String toString() {
		return this.expense;
	}
}
