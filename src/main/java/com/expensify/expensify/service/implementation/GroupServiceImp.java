package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.ExpenseDTO;
import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.dto.UserDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.repository.GroupRepository;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.service.ExpenseService;
import com.expensify.expensify.service.GroupService;
import com.expensify.expensify.service.UserService;

@Service
public class GroupServiceImp implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private ExpenseService expenseService;

	public Group GroupDTOToGroup(GroupDTO groupDTO) {
		Group group = new Group();
		group.setGroupName(groupDTO.getGrpName());
		group.setGroupType(groupDTO.getGrpType());

		List<User> users = new ArrayList<>(groupDTO.getGrpUser().size());

		for (String UserName : groupDTO.getGrpUser()) {
			User UserInstance = userRepository.findByUserName(UserName);
			UserInstance.getUserGroups().add(group);
			users.add(UserInstance);
		}
		group.setGroupUsers(users);
		group.setExpenses(null);
		return group;
	}

	public GroupDTO GroupToGroupDTO(Group group) {
		GroupDTO groupDTO = new GroupDTO();
		groupDTO.setId(group.getId());
		groupDTO.setGrpName(group.getGroupName());

		List<String> UserNames = new ArrayList<>();
		for (User u : group.getGroupUsers()) {
			UserNames.add(u.getUserName());
		}
		groupDTO.setGrpUser(UserNames);
		return groupDTO;
	}

	@Override
	public GroupDTO createGroup(GroupDTO groupModel) {
		Group group = this.GroupDTOToGroup(groupModel);

		groupRepository.save(group);
		return GroupToGroupDTO(group);
	}

	@Override
	public GroupDTO getGroupById(Long groupId) {
		return GroupToGroupDTO(groupRepository.findById(groupId).get());
	}

	@Override
	public List<UserDTO> addUser(Long grpId, List<Long> user) {
		Optional<Group> grp1 = groupRepository.findById(grpId);
		if (grp1.isPresent()) {
			grp1.get().getGroupUsers().addAll(userRepository.findAllById(user));
			groupRepository.save(grp1.get());
		}
		List<UserDTO> userDTOs = new ArrayList<>();

		for (User u : grp1.get().getGroupUsers()) {
			userDTOs.add(userService.UserToUserDTO(u));
		}

		return userDTOs;
	}

	@Override
	public List<UserDTO> getGroupUsers(Long groupId) {

		List<UserDTO> userDTOs = new ArrayList<>();

		for (User u : groupRepository.findById(groupId).get().getGroupUsers()) {
			userDTOs.add(userService.UserToUserDTO(u));
		}

		return userDTOs;
	}

	@Override
	public List<ExpenseDTO> getGroupExpenses(Long groupId) {
		List<ExpenseDTO> expenseDTOs = new ArrayList<>();

		for (Expense u : groupRepository.findById(groupId).get().getExpenses()) {
			expenseDTOs.add(expenseService.expenseToExpenseDTO(u));
		}

		return expenseDTOs;
	}

	@Override
	public String deleteGroup(Long groupId) {
		groupRepository.deleteById(groupId);
		return "Group Deleted";
	}
}
