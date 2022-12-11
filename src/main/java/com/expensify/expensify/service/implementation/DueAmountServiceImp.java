package com.expensify.expensify.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.DueAmountDTO;
import com.expensify.expensify.entity.DueAmount;
import com.expensify.expensify.entity.DueAmountPK;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.repository.DueAmountRepository;
import com.expensify.expensify.service.DueAmountService;
import com.expensify.expensify.service.UserService;

@Service
public class DueAmountServiceImp implements DueAmountService {

	@Autowired
	DueAmountRepository dueAmountRepository;

	@Autowired
	UserService userService;

	@Override
	public DueAmount updateAmount(User from, User to, double amount) {

		DueAmountPK dueAmountPK = new DueAmountPK(from, to);
		DueAmount dueAmount = new DueAmount();

		dueAmount = dueAmountRepository.findById(dueAmountPK).orElse(new DueAmount(dueAmountPK, 0));

		dueAmount.setAmount(dueAmount.getAmount() + amount);

		dueAmountRepository.save(dueAmount);

		return dueAmount;
	}

	@Override
	public List<DueAmountDTO> getAllDueAmount() {

		List<DueAmountDTO> dueAmounts = new ArrayList<>();

		for (DueAmount da : dueAmountRepository.findAll()) {
			dueAmounts.add(this.dueAmountToDueAmountDTO(da));
		}
		return dueAmounts;
	}

	@Override
	public List<DueAmountDTO> getAllDueAmountForm(Long userID) {
		List<DueAmountDTO> dueAmounts = new ArrayList<>();

		User user = new User();
		user.setId(userID);

		for (DueAmount da : dueAmountRepository.findBydueA1mountPK_UserFrom(user)) {
			dueAmounts.add(this.dueAmountToDueAmountDTO(da));
		}
		return dueAmounts;
	}

	public DueAmountDTO dueAmountToDueAmountDTO(DueAmount dueAmount) {
		DueAmountDTO dueAmountDTO = new DueAmountDTO();
		System.out.println(dueAmount);
		dueAmountDTO.setAmount(dueAmount.getAmount());
		dueAmountDTO.setUserFrom(userService.UserToUserDTO(dueAmount.getDueA1mountPK().getUserFrom()));
		dueAmountDTO.setUserTo(userService.UserToUserDTO(dueAmount.getDueA1mountPK().getUserTo()));
		return dueAmountDTO;
	}

	@Override
	public DueAmountDTO getAllDueAmountTo(User fromId, Long ToId) {

		DueAmountPK dueAmountPK = new DueAmountPK(fromId, userService.getUserById(ToId));
		return this.dueAmountToDueAmountDTO(dueAmountRepository.findById(dueAmountPK).get());
	}

}
