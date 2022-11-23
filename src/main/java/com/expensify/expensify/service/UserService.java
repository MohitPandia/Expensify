package com.expensify.expensify.service;

import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.Split;

import java.util.List;

public interface UserService {

    User createUser(UserDTO userModel);

    User getUserById(Long userId);

    List<Group> getUserGroups(Long userId);

    List<Split> getUserExpenses(Long userId);

    List<User> getAllUsers();

    String deleteUser(Long userId);

    User loadUserByUserName(String userName);
}
