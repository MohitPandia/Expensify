package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.ActivityDTO;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.entity.split.Split;

public interface ActivityService {

	void createActivity(User user, Expense expense, Split split);

	List<ActivityDTO> getALLActivityOfUser(User user);
}
