package com.expensify.expensify.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDTO {

	private Long id;
	@NotNull(message = "username is requied")
	private String userName;

	private double amount;
	private boolean status;

}
