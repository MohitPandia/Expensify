package com.expensify.expensify.entity.expense;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Data
@Embeddable
public class ExpenseData {
    private String name;
    public ExpenseData(String name){
        this.name = name;
    }

    public ExpenseData() {

    }
}
