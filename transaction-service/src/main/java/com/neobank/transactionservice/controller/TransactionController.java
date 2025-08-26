package com.neobank.transactionservice.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.transactionservice.dto.ApiResponse;
import com.neobank.transactionservice.dto.TransactionRequest;
import com.neobank.transactionservice.model.Transaction;
import com.neobank.transactionservice.service.TransactionService;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions") // All APIs will start with /transactions
@RequiredArgsConstructor
public class TransactionController {

	private final TransactionService transactionService;

	// create new transaction
	@PostMapping("/create")
	public ResponseEntity<String> createTransaction(@RequestBody TransactionRequest request) {
		transactionService.createTransaction(request);
		return ResponseEntity.ok("Transaction successfull");
	}

	// Get all transaction by accountnumber
	@GetMapping("/{accountNumber}")
	public List<Transaction> getTransactions(@PathVariable String accountNumber) {
		return transactionService.getByFromAccountNumberOrToAccountNumber(accountNumber);
	}
}
