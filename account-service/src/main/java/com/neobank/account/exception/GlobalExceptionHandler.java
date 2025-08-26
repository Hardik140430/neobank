package com.neobank.account.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties.Http;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.neobank.account.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<Map<String, String>> handleInsufficientBalance(InsufficientBalanceException e) {
		Map<String, String> response = new HashMap<>();
		response.put("error", e.getMessage());
		return new ResponseEntity<>(response, null, HttpStatus.SC_BAD_REQUEST);
	}

	// Handles validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, null, HttpStatus.SC_BAD_REQUEST);
	}

	// Handles custom business exceptions
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException e) {
		Map<String, String> respose = new HashMap<>();
		respose.put("error", e.getMessage());
		return new ResponseEntity<>(respose, null, HttpStatus.SC_NOT_FOUND);
	}

	// Handles all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleGeneralException(Exception e) {
		Map<String, String> response = new HashMap<>();
		response.put("error", e.getMessage());
		return new ResponseEntity<>(response, null, HttpStatus.SC_INTERNAL_SERVER_ERROR);
	}
	
	
	//Handles account not found
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleAccountNotFound(AccountNotFoundException e){
		Map<String, String> response = new HashMap<>();
		response.put("error", e.getMessage());
		return new ResponseEntity<>(response, null, HttpStatus.SC_NOT_FOUND);
	}
}
