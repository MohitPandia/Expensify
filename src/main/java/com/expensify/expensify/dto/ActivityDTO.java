package com.expensify.expensify.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
	private UserDTO user;
	private String message;
	private double amount;
	private Long expense;
	private Date timestamp;

}
