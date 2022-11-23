package com.expensify.expensify.entity;


public enum ExpenseType {
    EQUAL("EQUAL"),
    EXACT("EXACT"),
    PERCENT("PERCENT");

    private final String expense;

    ExpenseType(String s){
        expense=s;
    }
    public String toString(){
        return this.expense;
    }
}
