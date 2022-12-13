package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.dto.UserDTO;

public interface GroupService {
	GroupDTO createGroup(GroupDTO groupModel);

	GroupDTO getGroupById(Long groupId);

	List<UserDTO> addUser(Long grpId, List<Long> user);

	List<UserDTO> getGroupUsers(Long groupId);

	List<ExpenseDTO> getGroupExpenses(Long groupId);

	String deleteGroup(Long groupId);
}
