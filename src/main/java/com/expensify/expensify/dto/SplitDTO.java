package com.expensify.expensify.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SplitDTO {

	private Long user;
	@NotNull(message = "user id is requied")
	private String userName;
	private double amount;
	private double percent;
}
