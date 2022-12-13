package com.expensify.expensify.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupService groupService;

	@PostMapping("/create")
	public GroupDTO createGroup(@AuthenticationPrincipal User user, @RequestBody @Valid GroupDTO groupModel) {
		System.out.println(groupModel);
		groupModel.getGrpUser().add(user.getUserName());
		return groupService.createGroup(groupModel);
	}

	@PostMapping("/adduser/{id}")
	public List<UserDTO> addUser(@PathVariable("id") Long GrpId, @RequestBody List<Long> users) {
		return groupService.addUser(GrpId, users);
	}

	@GetMapping("/{id}")
	public GroupDTO getGroupById(@PathVariable("id") Long GroupId) {
		return groupService.getGroupById(GroupId);
	}

	@GetMapping("/users/{id}")
	public List<UserDTO> getGroupUsers(@PathVariable("id") Long GroupId) {
		return groupService.getGroupUsers(GroupId);
	}

	@GetMapping("/expenses/{id}")
	public List<ExpenseDTO> getGroupExpenses(@PathVariable("id") Long groupId) {
		return groupService.getGroupExpenses(groupId);
	}

	@DeleteMapping("/{id}")
	public String deleteGroup(@PathVariable("id") Long groupId) {
		return groupService.deleteGroup(groupId);
	}
}
