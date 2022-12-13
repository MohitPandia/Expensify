package com.expensify.expensify.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.EqualSplit;
import com.expensify.expensify.entity.split.ExactSplit;
import com.expensify.expensify.entity.split.PercentSplit;
import com.expensify.expensify.entity.split.SettleUPSplit;
import com.expensify.expensify.entity.split.Split;
import com.expensify.expensify.repository.UserRepository;
import com.expensify.expensify.repository.Split.SplitRepository;
import com.expensify.expensify.service.SplitService;

@Service
public class SplitServiceImp implements SplitService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SplitRepository splitRepository;

	@Override
	public SplitDTO splitToSplitDTO(Split split) {
		SplitDTO splitDTO = new SplitDTO();
		splitDTO.setAmount(split.getAmount());
		if (split instanceof PercentSplit) {
			splitDTO.setPercent(((PercentSplit) split).getPercent());
		}
		splitDTO.setUser(split.getUser().getId());
		splitDTO.setUserName(split.getUser().getUserName());
		return splitDTO;
	}

	@Override
	public Split createSplit(SplitDTO splitModel, ExpenseType type) {
		User user = userRepository.findByUserName(splitModel.getUserName());
		switch (type) {
		case EXACT:
			return new ExactSplit(user, splitModel.getAmount());
		case EQUAL:
			return new EqualSplit(user);
		case PERCENT:
			return new PercentSplit(user, splitModel.getPercent());
		case SETTLEUP:
			return new SettleUPSplit(user, splitModel.getAmount());
		}
		return null;
	}

	@Override
	public void deleteAllSplits(List<Split> splits) {

		splitRepository.deleteAllInBatch(splits);
	}
}
