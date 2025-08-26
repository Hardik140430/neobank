package com.neobank.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // Generates getters, setters, toString, etc.
@AllArgsConstructor // Generates constructor with all fields
public class AuthResponse {
	
	private String token;
	private String message; // Success or error message
	

}
