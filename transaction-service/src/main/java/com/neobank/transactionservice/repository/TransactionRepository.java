package com.neobank.transactionservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.transactionservice.model.Transaction;
import com.neobank.transactionservice.model.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	// Find all transactions where the account is either source or target
	List<Transaction> findByFromAccountNumberOrToAccountNumber(String fromAccountNumber, String toAccountNumber);

	// Optional: Find transactions by account and type
	List<Transaction> findByFromAccountNumberOrToAccountNumberAndTransactionType(String fromAccountNumber,
			String toAccountNumber, TransactionType transactionType);

}
