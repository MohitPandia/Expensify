package com.expensify.expensify.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

	@NotNull(message = "username is requied")
	@NotEmpty(message = "username is requied")
	private String userName;
	@NotNull(message = "password is requied")
	@NotEmpty(message = "password is requied")
	private String password;

}
