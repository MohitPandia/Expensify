package com.expensify.expensify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettleUPDTPO {
	private Long fromUserId;
	private Long toUserID;
	private double amount;
}
