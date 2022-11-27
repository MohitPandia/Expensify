package com.expensify.expensify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDTO {
	private String jWtToken;
	private UserDTO user;
}
