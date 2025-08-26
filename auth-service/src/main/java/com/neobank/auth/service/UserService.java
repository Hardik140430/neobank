package com.neobank.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.neobank.auth.dto.AuthResponse;
import com.neobank.auth.dto.LoginRequest;
import com.neobank.auth.dto.UserRequest;
import com.neobank.auth.model.Role;
import com.neobank.auth.model.User;
import com.neobank.auth.repository.UserRepository;
import com.neobank.auth.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private JwtUtil jwtUtil; // Injecting our JWT utility class

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public String registerUser(UserRequest request) {
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			return "Username already exists!";
		}

		User user = User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.email(request.getEmail()).role(Role.USER).build();

		userRepository.save(user);
		return "User registered successfully!";

	}
	
	
	public AuthResponse login(LoginRequest request) {
		//Find by username
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(()-> new RuntimeException("User not found"));
				
		//Match raw password with encrypted password stored in DB
		if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			// If password matches, generate a JWT token for the user
			String token = jwtUtil.generateToken(user.getUsername());
			
			// Return token and message in response DTO
			return new AuthResponse(token, "Login successful");
		}else {
			
			// If password doesn't match, return null token and error message
			return new AuthResponse(null, "Invalid credentials");
		}
	}

}
