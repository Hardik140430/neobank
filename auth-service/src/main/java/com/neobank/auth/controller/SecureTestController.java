package com.neobank.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure") // All endpoints in this class will be under /secure
public class SecureTestController {
	
	// Test endpoint to check if JWT-based authentication is working
	@GetMapping("/hello")
	public String sayHello() {		
		return "Welcome to NeoBank Secure Area!!";
	}

}
