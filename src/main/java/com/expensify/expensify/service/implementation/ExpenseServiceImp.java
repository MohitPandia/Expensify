package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.expensify.expensify.Exception.User.ExpenseServiceException;
import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.dto.ExpenseDataDTO;
import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseStatus;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.EqualExpense;
import com.expensify.expensify.entity.expense.ExactExpense;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.entity.expense.ExpenseData;
import com.expensify.expensify.entity.expense.PercentageExpense;
import com.expensify.expensify.entity.expense.SettleUpExpense;
import com.expensify.expensify.entity.split.PercentSplit;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.GroupRepository;
import com.expensify.expensify.repository.Expense.ExpenseRepository;
import com.expensify.expensify.service.ActivityService;
import com.expensify.expensify.service.DueAmountService;
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
	GroupRepository groupRepository;

	@Autowired
	GroupService groupService;

	@Autowired
	ActivityService activityService;

	@Autowired
	DueAmountService dueAmountService;

	public ExpenseData ExpenseDataDTOToExpenseData(ExpenseDataDTO expenseDataDTO) {
		ExpenseData expenseData = new ExpenseData();
		expenseData.setDescription(expenseDataDTO.getDescription());
		return expenseData;
	}

	public ExpenseDataDTO expenseDataToExpenseDataDTO(ExpenseData expenseData) {
		ExpenseDataDTO expenseDataDTO = new ExpenseDataDTO();
		expenseDataDTO.setDescription(expenseData.getDescription());
		return expenseDataDTO;

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
		expense.setExpenseType(expenseType);
		expense.setExpenseName(expenseDTO.getExpName());
		expense.setExpGroup(groupRepository.findById(expenseDTO.getExpGrp()).get());
		for (Split split : expense.getSplits()) {
			split.setExpense(expense);
		}
		return expense;
	}

//	@Override
	public Expense createExpense(ExpenseType expenseType, ExpenseDTO expenseDTO, List<Split> splits) {
		double amount = expenseDTO.getExpAmt();
		User expensePaidBy = userService.loadUserByUserName(expenseDTO.getExpPaidBy());
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
			PercentageExpense percentageExpense = new PercentageExpense(amount, expensePaidBy, splits, expenseData);
			return percentageExpense;
		case EQUAL:
			int totalSplits = splits.size();
			double splitAmount = (Math.round(amount * 100 / totalSplits) / 100.0);
			for (Split split : splits) {
				split.setAmount(splitAmount);
			}
			EqualExpense equalExpense = new EqualExpense(amount, expensePaidBy, splits, expenseData);
			return equalExpense;
		case SETTLEUP:
			SettleUpExpense settleUpExpense = new SettleUpExpense(amount, expensePaidBy, splits, expenseData);
			return settleUpExpense;
		default:
			return null;
		}
	}

	@Override
	public ExpenseDTO expenseToExpenseDTO(Expense expense) {

		ExpenseDTO expenseDTO = new ExpenseDTO();
		expenseDTO.setId(expense.getId());
		expenseDTO.setExpAmt(expense.getAmount());
		expenseDTO.setExpenseData(this.expenseDataToExpenseDataDTO(expense.getExpenseData()));
		expenseDTO.setExpName(expense.getExpenseName());
		expenseDTO.setExpGrp(expense.getExpGroup().getId());
		expenseDTO.setExpPaidBy(expense.getExpensePaidBy().getUserName());
		expenseDTO.setExpType(expense.getExpenseType().name());

		System.out.println(expense.getExpenseStatus());
		expenseDTO.setStatus(expense.getExpenseStatus().toString());

		List<SplitDTO> splitDTOs = new ArrayList<>();

		for (Split split : expense.getSplits()) {
			splitDTOs.add(splitService.splitToSplitDTO(split));
		}

		expenseDTO.setUsrSplitBtw(splitDTOs);
		return expenseDTO;
	}

	@Override
	public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
		Expense expense = this.ExpenseDTOToExpense(expenseDTO);
		if (expense.validate() && expense.getExpenseType().compareTo(ExpenseType.SETTLEUP) != 0) {
			expense.setExpenseStatus(ExpenseStatus.CREATED);
			for (Split s : expense.getSplits()) {
				dueAmountService.updateAmount(expense.getExpensePaidBy(), s.getUser(), (-1) * s.getAmount());
				dueAmountService.updateAmount(s.getUser(), expense.getExpensePaidBy(), s.getAmount());
				activityService.createActivity(s.getUser(), expense, s);
			}
			activityService.createActivity(expense.getExpensePaidBy(), expense, null);

			expenseRepository.save(expense);
		} else {
			return null;
		}
		return expenseToExpenseDTO(expense);
	}

	@Override
	public ExpenseDTO UpdateExpense(Long Expenseid, ExpenseDTO expenseDTO) {

		Expense expense = expenseRepository.findById(Expenseid).get();
		for (Split s : expense.getSplits()) {
			dueAmountService.updateAmount(expense.getExpensePaidBy(), s.getUser(), s.getAmount());
			dueAmountService.updateAmount(s.getUser(), expense.getExpensePaidBy(), (-1) * s.getAmount());
		}

		splitService.deleteAllSplits(expense.getSplits());

//		expense.setExpenseData(ExpenseDataDTOToExpenseData(expenseDTO.getExpenseData()));
//		expense.setAmount(expenseDTO.getExpAmt());
//		expense.setExpenseName(expenseDTO.getExpName());
//		expense.setExpensePaidBy(userService.getUserById(expenseDTO.getExpPaidBy()));
//		expense.setExpenseType(ExpenseType.valueOf(expenseDTO.getExpType()));
//		expense.setExpGroup(groupService.getGroupById(expenseDTO.getId()));
//		expense.setSplits(null);

		Expense exp = ExpenseDTOToExpense(expenseDTO);

		if (exp.validate()) {
			exp.setId(Expenseid);
			expense.setExpenseData(exp.getExpenseData());
			expense.setAmount(exp.getAmount());
			expense.setExpenseName(exp.getExpenseName());
			expense.setExpensePaidBy(exp.getExpensePaidBy());
			expense.setExpenseType(exp.getExpenseType());
			expense.setExpGroup(exp.getExpGroup());
			expense.setSplits(exp.getSplits());
			expense.setExpenseStatus(ExpenseStatus.UPDATED);
			for (Split s : expense.getSplits()) {
				dueAmountService.updateAmount(expense.getExpensePaidBy(), s.getUser(), (-1) * s.getAmount());
				dueAmountService.updateAmount(s.getUser(), expense.getExpensePaidBy(), s.getAmount());
				activityService.createActivity(s.getUser(), expense, s);
			}
			activityService.createActivity(expense.getExpensePaidBy(), expense, null);

			expenseRepository.save(expense);
		}
		return expenseToExpenseDTO(expense);
	}

	@Override
	public ExpenseDTO DeleteExpense(Long expenseId) {

		Expense expense = expenseRepository.findById(expenseId).get();
		for (Split s : expense.getSplits()) {
			dueAmountService.updateAmount(expense.getExpensePaidBy(), s.getUser(), s.getAmount());
			dueAmountService.updateAmount(s.getUser(), expense.getExpensePaidBy(), (-1) * s.getAmount());
			activityService.createActivity(s.getUser(), expense, s);
		}
		activityService.createActivity(expense.getExpensePaidBy(), expense, null);
		expense.setExpenseStatus(ExpenseStatus.DELETED);
		expenseRepository.save(expense);
		return expenseToExpenseDTO(expense);
	}

	@Override
	public ExpenseDTO FindExpense(Long expenseId) {
		return expenseToExpenseDTO(expenseRepository.findById(expenseId).get());
	}

	@Override
	public List<ExpenseDTO> getGrpExpenses(Long groupId) {
		return groupService.getGroupExpenses(groupId);
	}

	@Override
	public ExpenseDTO resolveExpense(ExpenseDTO expenseDTO) {

		Expense expense = this.ExpenseDTOToExpense(expenseDTO);

		if (expense.validate() && expense.getExpenseType().compareTo(ExpenseType.SETTLEUP) == 0) {

			if (expense.getSplits().size() >= 1) {
				throw new ExpenseServiceException("Settle up requied only one user");
			}

			Split s = expense.getSplits().get(0);
			dueAmountService.updateAmount(expense.getExpensePaidBy(), s.getUser(), s.getAmount());
			dueAmountService.updateAmount(s.getUser(), expense.getExpensePaidBy(), (-1) * s.getAmount());
			activityService.createActivity(s.getUser(), expense, s);
			expenseRepository.save(expense);
		} else {
			return null;
		}
		return expenseToExpenseDTO(expense);
	}

	@Override
	public List<ExpenseDTO> findAllExpenseOfUser(Long userid) {

		List<ExpenseDTO> expenseDTOs = new ArrayList<>();

		for (Expense expense : expenseRepository.findExpenseByUserIdAndSort(userid,
				Sort.by("timestamp").descending())) {
			expenseDTOs.add(this.expenseToExpenseDTO(expense));
		}
		return expenseDTOs;
	}
}
