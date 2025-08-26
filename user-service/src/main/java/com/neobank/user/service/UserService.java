package com.neobank.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neobank.user.dto.UserRequest;
import com.neobank.user.model.User;
import com.neobank.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public String createUser(UserRequest userRequest) {
		if (userRepository.existsByEmail(userRequest.getEmail())) {
			return "User already exists!";
		}
		
		User user = User.builder()
				.fullName(userRequest.getFullName())
				.email(userRequest.getEmail())
				.mobile(userRequest.getMobile())
				.address(userRequest.getAddress())
				.CreationDate(LocalDateTime.now())
				.build();
		
		userRepository.save(user);
		return "User created successfully!";
	}
	
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	
}
