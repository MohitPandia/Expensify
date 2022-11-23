package com.expensify.expensify.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.EqualSplit;
import com.expensify.expensify.entity.split.ExactSplit;
import com.expensify.expensify.entity.split.PercentSplit;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.service.SplitService;

@Service
public class SplitServiceImp implements SplitService {

	@Autowired
	UserRepository userRepository;

	@Override
	public Split createSplit(SplitDTO splitModel, ExpenseType type) {
		User user = userRepository.findById(splitModel.getUser()).get();
		switch (type) {
		case EXACT:
			return new ExactSplit(user, splitModel.getAmount());
		case EQUAL:
			System.out.println("i ma here");
			return new EqualSplit(user);
		case PERCENT:
			return new PercentSplit(user, splitModel.getPercent());
		}
		return null;
	}
}
