package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.GroupDTO;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;
import com.expensify.expensify.repository.GroupRepository;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.service.GroupService;

@Service
public class GroupServiceImp implements GroupService {

	@Autowired
	private GroupRepository groupRepository;
	@Autowired
	private UserRepository userRepository;

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

	@Override
	public Group createGroup(GroupDTO groupModel) {
		Group group = this.GroupDTOToGroup(groupModel);
		groupRepository.save(group);
		return group;
	}

	@Override
	public Group getGroupById(Long groupId) {
		return groupRepository.findById(groupId).get();
	}

	@Override
	public List<User> addUser(Long grpId, List<Long> user) {
		Optional<Group> grp1 = groupRepository.findById(grpId);
		if (grp1.isPresent()) {
			grp1.get().getGroupUsers().addAll(userRepository.findAllById(user));
			groupRepository.save(grp1.get());
		}
		return grp1.get().getGroupUsers();
	}

	@Override
	public List<User> getGroupUsers(Long groupId) {
		return groupRepository.findById(groupId).get().getGroupUsers();
	}

	@Override
	public List<Expense> getGroupExpenses(Long groupId) {
		return groupRepository.findById(groupId).get().getExpenses();
	}

	@Override
	public String deleteGroup(Long groupId) {
		groupRepository.deleteById(groupId);
		return "Group Deleted";
	}
}
