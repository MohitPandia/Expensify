package com.expensify.expensify.entity.expense;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Data
@Embeddable
public class ExpenseData {
    private String description;
    public ExpenseData(String name){
        this.description = name;
    }

    public ExpenseData() {

    }
}
