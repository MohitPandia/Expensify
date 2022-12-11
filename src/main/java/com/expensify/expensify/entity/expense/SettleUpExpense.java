package com.expensify.expensify.entity.expense;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.SettleUPSplit;
import com.expensify.expensify.entity.split.Split;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("SettleUp")
public class SettleUpExpense extends Expense {

	public SettleUpExpense() {

	}

	public SettleUpExpense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData) {
		super(amount, expensePaidBy, splits, expenseData);
	}

	@Override
	public boolean validate() {
		for (Split split : getSplits()) {
			if (!(split instanceof SettleUPSplit))
				return false;
		}
		return true;
	}
}
