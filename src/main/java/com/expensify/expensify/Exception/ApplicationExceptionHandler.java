package com.expensify.expensify.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.expensify.expensify.Exception.User.UserServiceException;
import com.expensify.expensify.dto.ErrorResponseDTO;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponseDTO handleAuthenticationFailureException(AccessDeniedException ex) {

		return new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}

	@ExceptionHandler(value = UserServiceException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorResponseDTO handleUserServiceException(UserServiceException ex) {
		return new ErrorResponseDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
	}

}
