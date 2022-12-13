package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expensify.expensify.Exception.User.UserNotFoundException;
import com.expensify.expensify.Exception.User.UserServiceException;
import com.expensify.expensify.dto.AddFriendDTO;
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
		userDTO.setId(user.getId());
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

		if (userRepository.findByUserNameAndUserEmail(userDTO.getUserName(), userDTO.getUserEmail()).size() > 0) {
			throw new UserServiceException("username or useremail is not unique");
		}

		User user = this.UserDTOTOUser(userDTO);
		user.setRole(UserRoles.USER.toString());
		System.out.println(user);
		userRepository.save(user);
		return user;
	}

	@Override
	public UserDTO updateUser(User user, UserDTO userDTO) {

		user.setUserFirstName(userDTO.getUserFirstName());
		user.setUserLastName(userDTO.getUserLastName());
//		user.setUserName(userDTO.getUserName());
//		user.setUserEmail(userDTO.getUserEmail());
		user.setUserMobileNumber(userDTO.getUserMobileNumber());
//		user.setUserPassword(passwordEncoder.encode(userDTO.getUserPassword()));
		userRepository.save(user);
		return UserToUserDTO(user);
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User with user id " + userId + " Not Found"));
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

	@Override
	public List<UserDTO> getAllFriendList(User user) {
//		System.out.println(user);
		List<User> friends = user.getFriends();
		List<UserDTO> userDTOs = new ArrayList<>();
		if (friends.isEmpty()) {
			return userDTOs;
		}
		for (User u : friends) {
			userDTOs.add(UserToUserDTO(u));
		}
		return userDTOs;
	}

	@Override
	public AddFriendDTO addFriend(User user, AddFriendDTO addFriendDTO) {
		User friend = userRepository.findByUserName(addFriendDTO.getUserName());

		addFriendDTO.setStatus(false);
		if (friend == null) {
			return addFriendDTO;
		}
		addFriendDTO.setStatus(true);

		for (User u : user.getFriends()) {
			if (u.getId() == friend.getId()) {
				throw new UserServiceException("This user is already your friend");
			}
		}
		user.getFriends().add(friend);
		userRepository.save(user);
		return addFriendDTO;
	}

}
