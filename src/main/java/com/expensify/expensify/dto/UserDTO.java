package com.expensify.expensify.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
	@NotNull(message = "First Name is required")
	private String userFirstName;
	@NotNull(message = "Last name is required")
	private String userLastName;
	@NotNull(message = "username is required")
	private String userName;

	@Email(message = "user email address is not proper")
	@NotNull(message = "user email address is required")
	private String userEmail;

	@NotNull(message = "password is required")
	private String userPassword;

	@Pattern(regexp = "^\\d{10}$", message = "invalid mobile number entered ")
	private String userMobileNumber;
}
