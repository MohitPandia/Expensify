package com.expensify.expensify.service;

import com.expensify.expensify.dto.SplitDTO;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.split.Split;

public interface SplitService {

    Split createSplit(SplitDTO splitModel, ExpenseType type);
}
