package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.dto.ExpenseDataDTO;
import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.EqualExpense;
import com.expensify.expensify.entity.expense.ExactExpense;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.entity.expense.ExpenseData;
import com.expensify.expensify.entity.expense.PercentageExpense;
import com.expensify.expensify.entity.split.PercentSplit;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.Expense.ExpenseRepository;
import com.expensify.expensify.service.ExpenseService;
import com.expensify.expensify.service.GroupService;
import com.expensify.expensify.service.SplitService;
import com.expensify.expensify.service.UserService;

@Service
public class ExpenseServiceImp implements ExpenseService {

	@Autowired
	ExpenseRepository expenseRepository;

	@Autowired
	SplitService splitService;

	@Autowired
	UserService userService;

	@Autowired
	GroupService groupService;

	public ExpenseData ExpenseDataDTOToExpenseData(ExpenseDataDTO expenseDataDTO) {
		ExpenseData expenseData = new ExpenseData();

		expenseData.setName(expenseDataDTO.getName());

		return expenseData;
	}

	public Expense ExpenseDTOToExpense(ExpenseDTO expenseDTO) {

		ExpenseType expenseType = ExpenseType.valueOf(expenseDTO.getExpType());

		List<Split> splits = new ArrayList<>(expenseDTO.getUsrSplitBtw().size());

		for (SplitDTO splitdto : expenseDTO.getUsrSplitBtw()) {
			Split split = splitService.createSplit(splitdto, expenseType);
			splits.add(split);
		}

		Expense expense = this.createExpense(expenseType, expenseDTO, splits);

		expense.setAmount(expenseDTO.getExpAmt());

		expense.setExpGroup(groupService.getGroupById(expenseDTO.getExpGrp()));
		for (Split split : expense.getSplits()) {
			split.setExpense(expense);
		}
		return expense;
	}

	@Override
	public Expense createExpense(ExpenseDTO expenseDTO) {

		Expense expense = this.ExpenseDTOToExpense(expenseDTO);

		if (expense.validate()) {
			expenseRepository.save(expense);
		} else {
			return null;
		}
		return expense;
	}

	@Override
	public Expense createExpense(ExpenseType expenseType, ExpenseDTO expenseDTO, List<Split> splits) {
		double amount = expenseDTO.getExpAmt();
		User expensePaidBy = userService.getUserById(expenseDTO.getExpPaidBy());
		ExpenseData expenseData = this.ExpenseDataDTOToExpenseData(expenseDTO.getExpenseData());
		switch (expenseType) {
		case EXACT:
			ExactExpense expense = new ExactExpense(amount, expensePaidBy, splits, expenseData);
			return expense;
		case PERCENT:
			for (Split split : splits) {
				PercentSplit percentSplit = (PercentSplit) split;
				split.setAmount((amount * percentSplit.getPercent()) / 100.0);
			}
			PercentageExpense pex = new PercentageExpense(amount, expensePaidBy, splits, expenseData);
			return pex;
		case EQUAL:
			int totalSplits = splits.size();
			double splitAmount = ((double) Math.round(amount * 100 / totalSplits) / 100.0);
			for (Split split : splits) {
				split.setAmount(splitAmount);
			}
			EqualExpense eex = new EqualExpense(amount, expensePaidBy, splits, expenseData);
			return eex;
		default:
			return null;
		}
	}

	@Override
	public List<Expense> getGrpExpenses(Long groupId) {
		return groupService.getGroupExpenses(groupId);
	}

	@Override
	public String resolveExpense(Long expId) {
		return null;
	}
}
