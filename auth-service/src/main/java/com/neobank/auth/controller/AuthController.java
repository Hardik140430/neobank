package com.neobank.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.auth.dto.AuthResponse;
import com.neobank.auth.dto.LoginRequest;
import com.neobank.auth.dto.UserRequest;
import com.neobank.auth.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
		
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRequest userRequest){
		String result = userService.registerUser(userRequest);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
		
		AuthResponse response = userService.login(request);
	
		// Return HTTP 200 with the token and message
		return ResponseEntity.ok(response);
	}
}
