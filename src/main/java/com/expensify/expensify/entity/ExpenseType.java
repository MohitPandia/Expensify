package com.expensify.expensify.entity;

public enum ExpenseType {
	EQUAL("EQUAL"), EXACT("EXACT"), PERCENT("PERCENT"), SETTLEUP("SETTLEUP");

	private final String expense;

	ExpenseType(String s) {
		expense = s;
	}

	@Override
	public String toString() {
		return this.expense;
	}
}
