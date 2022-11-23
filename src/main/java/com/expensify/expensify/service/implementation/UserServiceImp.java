package com.expensify.expensify.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.service.UserService;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(UserDTO userModel) {
		User user = new User();

		user.setUserFirstName(userModel.getUserFirstName());
		user.setUserLastName(userModel.getUserLastName());
		user.setUserName(userModel.getUserName());
		user.setUserPassword(userModel.getUserPassword());
		user.setUserMobileNumber(userModel.getUserMobileNumber());
		user.setUserEmail(userModel.getUserEmail());
		user.setUserGroups(null);
		userRepository.save(user);
		return user;
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId).get();
	}

	@Override
	public List<Group> getUserGroups(Long userId) {
		return userRepository.findById(userId).get().getUserGroups();
	}

	@Override
	public List<Split> getUserExpenses(Long userId) {
		return userRepository.findById(userId).get().getExpenses();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public String deleteUser(Long userId) {
		userRepository.deleteById(userId);
		return "user deleted";
	}

	@Override
	public User loadUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
