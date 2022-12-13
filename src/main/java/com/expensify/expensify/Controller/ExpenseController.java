package com.expensify.expensify.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.service.ExpenseService;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@PostMapping("/create")
	public ExpenseDTO createExpense(@RequestBody @Valid ExpenseDTO expenseModel) {
		return expenseService.createExpense(expenseModel);
	}

	@PostMapping("/update/{id}")
	public ExpenseDTO updateExpense(@PathVariable("id") Long id, @RequestBody @Valid ExpenseDTO expenseModel) {
		return expenseService.UpdateExpense(id, expenseModel);
	}

	@GetMapping("/group/{id}")
	public List<ExpenseDTO> getGrpExpenses(@PathVariable("id") Long groupId) {
		return expenseService.getGrpExpenses(groupId);
	}

	@DeleteMapping("/{id}")
	public ExpenseDTO deleteExpense(@PathVariable("id") Long id) {
		return expenseService.DeleteExpense(id);
	}

	@GetMapping("/{id}")
	public ExpenseDTO getExpense(@PathVariable("id") Long id) {
		return expenseService.FindExpense(id);
	}

	@PostMapping("/resolve")
	public ExpenseDTO resolveExpense(@RequestBody @Valid ExpenseDTO expenseModel) {
		return expenseService.resolveExpense(expenseModel);
	}
}
