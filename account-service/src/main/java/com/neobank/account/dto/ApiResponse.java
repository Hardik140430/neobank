package com.neobank.account.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
	
	private String status;     // e.g. "SUCCESS" or "ERROR"
    private String message;    // human-readable message
    private Instant timestamp; // when the response was created

}
