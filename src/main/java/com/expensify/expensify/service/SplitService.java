package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.split.Split;

public interface SplitService {

	SplitDTO splitToSplitDTO(Split split);

	Split createSplit(SplitDTO splitModel, ExpenseType type);

	void deleteAllSplits(List<Split> splits);
}
