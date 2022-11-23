package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;

public interface GroupService {
	Group createGroup(GroupDTO groupModel);

	Group getGroupById(Long groupId);

	List<User> addUser(Long grpId, List<Long> user);

	List<User> getGroupUsers(Long groupId);

	List<Expense> getGroupExpenses(Long groupId);

	String deleteGroup(Long groupId);
}
