package com.expensify.expensify.service;

import java.util.List;

import com.expensify.expensify.dto.DueAmountDTO;
import com.expensify.expensify.entity.DueAmount;
import com.expensify.expensify.entity.User;

public interface DueAmountService {

	DueAmount updateAmount(User from, User to, double amount);

	List<DueAmountDTO> getAllDueAmount();

	List<DueAmountDTO> getAllDueAmountForm(Long userID);

	DueAmountDTO getAllDueAmountTo(User FomrId, Long ToId);
}
