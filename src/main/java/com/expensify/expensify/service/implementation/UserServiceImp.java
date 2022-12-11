package com.expensify.expensify.service.implementation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensify.expensify.Exception.User.UserNotFound;
import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.dto.UserLoginDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.UserRoles;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.service.UserService;

@Service
public class UserServiceImp implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User UserDTOTOUser(UserDTO userDTO) {
		User user = new User();

		user.setUserFirstName(userDTO.getUserFirstName());
		user.setUserLastName(userDTO.getUserLastName());
		user.setUserName(userDTO.getUserName());
		user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
		user.setUserMobileNumber(userDTO.getUserMobileNumber());
		user.setUserEmail(userDTO.getUserEmail());
		user.setUserGroups(null);
		return user;
	}

	@Override
	public UserDTO UserToUserDTO(User user) {

		UserDTO userDTO = new UserDTO();
		userDTO.setUserEmail(user.getUserEmail());
		userDTO.setUserFirstName(user.getUserFirstName());
		userDTO.setUserLastName(user.getUserLastName());
		userDTO.setUserName(user.getUsername());
		userDTO.setUserMobileNumber(user.getUserMobileNumber());

		return userDTO;
	}

	@Override
	public User createUser(@Valid UserDTO userDTO) {
		System.out.println(userDTO);
		User user = this.UserDTOTOUser(userDTO);
		user.setRole(UserRoles.USER.toString());
		System.out.println(user);
		userRepository.save(user);
		return user;
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound("User with user id " + userId + " Not Found"));
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
	public User userLogin(UserLoginDTO userLogin) {
		// TODO Auto-generated method stub
//
		User user = userRepository.findByUserName(userLogin.getUserName());
		if (user == null) {
			return null;
		}
		if (passwordEncoder.matches(userLogin.getPassword(), user.getUserPassword())) {
			System.out.println("password matched");
			return user;
		}
		return null;
	}

	@Override
	public User loadUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

}
