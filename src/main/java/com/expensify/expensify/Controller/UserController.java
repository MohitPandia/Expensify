package com.expensify.expensify.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public User registerUser(@RequestBody @Valid UserDTO userModel) {
		userModel.setUserName(userModel.getUserFirstName() + " " + userModel.getUserLastName());
		User user = userService.createUser(userModel);

		return user;
	}

	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") Long userId) {
		return userService.getUserById(userId);
	}

	@GetMapping("/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/groups/{id}")
	public List<Group> getUserGroups(@PathVariable("id") Long userId) {
		return userService.getUserGroups(userId);
	}

	@GetMapping("/expenses/{id}")
	public List<Split> getUserExpenses(@PathVariable("id") Long userId) {
		return userService.getUserExpenses(userId);
	}

	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable("id") Long userId) {
		return userService.deleteUser(userId);
	}
}
