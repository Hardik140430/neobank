package com.neobank.transactionservice.dto;

import java.math.BigDecimal;

import com.neobank.transactionservice.model.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

	@NotBlank(message = "Transaction ID cannot be blank")
	private String transactionId;
		
	private String fromAccountNumber; // Account on which transaction will happen
	
	 private String toAccountNumber;
	
	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;    // Amount to credit/debit
		
    private TransactionType transactionType; // DEPOSIT/WITHDRAWAL/TRANSFER
    private String description;   // Optional description

}
