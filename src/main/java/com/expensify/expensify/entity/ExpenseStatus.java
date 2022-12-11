package com.expensify.expensify.entity;

public enum ExpenseStatus {
	CREATED("CREATED"), UPDATED("UPDATED"), DELETED("DELETED");

	private final String expenseStatus;

	ExpenseStatus(String s) {
		expenseStatus = s;
	}

	@Override
	public String toString() {
		return this.expenseStatus;
	}
}
