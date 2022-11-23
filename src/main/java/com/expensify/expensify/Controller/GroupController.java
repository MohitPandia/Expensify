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

import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/create")
	public Group createGroup(@RequestBody @Valid GroupDTO groupModel) {
		System.out.println(groupModel);
		return groupService.createGroup(groupModel);
	}

	@PostMapping("/adduser/{id}")
	public List<User> addUser(@PathVariable("id") Long GrpId, @RequestBody List<Long> users) {
		return groupService.addUser(GrpId, users);
	}

	@GetMapping("/{id}")
	public Group getGroupById(@PathVariable("id") Long GroupId) {
		return groupService.getGroupById(GroupId);
	}

	@GetMapping("/users/{id}")
	public List<User> getGroupUsers(@PathVariable("id") Long GroupId) {
		return groupService.getGroupUsers(GroupId);
	}

	@GetMapping("/expenses/{id}")
	public List<Expense> getGroupExpenses(@PathVariable("id") Long groupId) {
		return groupService.getGroupExpenses(groupId);
	}

	@DeleteMapping("/{id}")
	public String deleteGroup(@PathVariable("id") Long groupId) {
		return groupService.deleteGroup(groupId);
	}
}
