package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.dto.UserLoginDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.Split;

public interface UserService {
	User UserDTOTOUser(UserDTO userDTO);

	UserDTO UserToUserDTO(User user);

	User createUser(UserDTO userModel);

	User getUserById(Long userId);

	List<Group> getUserGroups(Long userId);

	List<Split> getUserExpenses(Long userId);

	List<User> getAllUsers();

	String deleteUser(Long userId);

	User userLogin(UserLoginDTO userLogin);

	User loadUserByUserName(String userName);
}
