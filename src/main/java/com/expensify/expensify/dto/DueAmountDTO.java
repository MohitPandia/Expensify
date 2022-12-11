package com.expensify.expensify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DueAmountDTO {

	private UserDTO userFrom;
	private UserDTO userTo;
	private double amount;

}
