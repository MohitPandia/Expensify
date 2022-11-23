package com.expensify.expensify.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.service.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/create")
	public Expense createExpense(@RequestBody @Valid ExpenseDTO expenseModel) {
		return expenseService.createExpense(expenseModel);
	}

	@GetMapping("/group/{id}")
	public List<Expense> getGrpExpenses(@PathVariable("id") Long groupId) {
		return expenseService.getGrpExpenses(groupId);
	}

	@GetMapping("/resolve/{id}")
	public String resolveExpense(@PathVariable("id") Long expId) {
		return expenseService.resolveExpense(expId);
	}
}