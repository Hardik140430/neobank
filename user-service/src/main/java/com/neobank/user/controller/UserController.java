package com.neobank.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.user.dto.UserRequest;
import com.neobank.user.model.User;
import com.neobank.user.repository.UserRepository;
import com.neobank.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/create")
	public String createUser(@RequestBody UserRequest userRequest) {
		System.out.println("in createee");
		return userService.createUser(userRequest);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		System.out.println("UserController.getUserById " + id);
		return userService.getUserById(id).map(user -> ResponseEntity.ok(user))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
