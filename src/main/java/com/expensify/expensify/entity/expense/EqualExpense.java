package com.expensify.expensify.entity.expense;

import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.EqualSplit;
import com.expensify.expensify.entity.split.Split;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("EQUAL")
public class EqualExpense extends  Expense{

    public EqualExpense(){

    }

    public EqualExpense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData){
        super(amount,expensePaidBy,splits,expenseData);
    }

    @Override
    public boolean validate() {
        for (Split split: getSplits()){
            if(!(split instanceof EqualSplit)) return false;
        }
        return true;
    }
}
