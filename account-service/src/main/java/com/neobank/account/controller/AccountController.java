package com.neobank.account.controller;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.account.dto.AccountRequest;
import com.neobank.account.dto.ApiResponse;
import com.neobank.account.dto.BalanceUpdateRequest;
import com.neobank.account.dto.User;
import com.neobank.account.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	// POST endpoint to create a new account
	@PostMapping("/create")
	public String createAccount(@Valid @RequestBody AccountRequest request) {		
		// Call the service method to create the account and return the response
		return accountService.createAccount(request);
	}
	
	@PostMapping("/updateBalance")
	public ResponseEntity<ApiResponse> updateBalance(@Valid @RequestBody BalanceUpdateRequest request){
		accountService.updateBalance(request);
		
		ApiResponse response = new ApiResponse(
				"SUCCESS",
				"Balance updated successfully",
				Instant.now());
		
		return ResponseEntity.ok(response);
	}
		
}
