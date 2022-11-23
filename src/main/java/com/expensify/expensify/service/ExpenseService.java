package com.expensify.expensify.service;

import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.ExpenseData;
import com.expensify.expensify.entity.split.Split;

import java.util.List;

public interface ExpenseService {
    Expense createExpense(ExpenseDTO expenseModel);
    Expense createExpense(ExpenseType expenseType, ExpenseDTO expenseModel,List<Split> splits);
    List<Expense> getGrpExpenses(Long groupId);

    String resolveExpense(Long expId);
}
