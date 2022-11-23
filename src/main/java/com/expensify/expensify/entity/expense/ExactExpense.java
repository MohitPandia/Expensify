package com.expensify.expensify.entity.expense;

import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.ExactSplit;
import com.expensify.expensify.entity.split.Split;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("EXACT")
public class ExactExpense extends Expense {

    public ExactExpense() {
    }

    public ExactExpense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData){
        super(amount,expensePaidBy,splits,expenseData);
    }

    @Override
    public boolean validate() {
        double totalAmount = getAmount();
        double totalSplitAmount = 0;
        for(Split split : getSplits()){
            if(!(split instanceof ExactSplit)) return false;
            ExactSplit exactSplit = (ExactSplit) split;
            totalSplitAmount += exactSplit.getAmount();
        }
        return totalAmount == totalSplitAmount;
    }
}
