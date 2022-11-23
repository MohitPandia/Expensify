package com.expensify.expensify.entity.expense;

import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.PercentSplit;
import com.expensify.expensify.entity.split.Split;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
@DiscriminatorValue("PERCENT")
public class PercentageExpense extends Expense{

    public PercentageExpense() {
    }

    public PercentageExpense(double amount , User expensePaidBy, List<Split> splits, ExpenseData expenseData){
        super(amount,expensePaidBy,splits,expenseData);
    }

    @Override
    public boolean validate() {
        double totalSplitPercent =0 ;
        for(Split split : getSplits()){
            if(!(split instanceof PercentSplit)) return false;
            PercentSplit percentSplit = (PercentSplit) split;
            totalSplitPercent += percentSplit.getPercent();
        }
        return 100 == totalSplitPercent;
    }
}
