package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.entity.expense.Expense;

public interface ExpenseService {
	ExpenseDTO createExpense(ExpenseDTO expenseModel);

//	Expense createExpense(ExpenseType expenseType, ExpenseDTO expenseModel, List<Split> splits);

	List<Expense> getGrpExpenses(Long groupId);

	ExpenseDTO resolveExpense(ExpenseDTO expenseModel);

	ExpenseDTO UpdateExpense(Long id, ExpenseDTO expenseDTO);

	ExpenseDTO DeleteExpense(Long expenseId);

	ExpenseDTO FindExpense(Long expenseId);

	ExpenseDTO expenseToExpenseDTO(Expense expense);

	List<ExpenseDTO> findAllExpenseOfUser(Long userid);
}
