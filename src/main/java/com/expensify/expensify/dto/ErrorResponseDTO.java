package com.expensify.expensify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
	private int statusCode;
	private String message;

	public ErrorResponseDTO(String message) {
		super();
		this.message = message;
	}
}
