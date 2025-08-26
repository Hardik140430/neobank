package com.neobank.account.dto;

import java.math.BigDecimal;

import com.neobank.account.model.TransactionType;

import lombok.Data;

@Data
public class BalanceUpdateRequest {

	
	private String fromAccountNumber;
	private String toAccountNumber;
	private TransactionType transactionType;
	private BigDecimal amount;
}
