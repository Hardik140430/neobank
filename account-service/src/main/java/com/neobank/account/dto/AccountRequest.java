package com.neobank.account.dto;

import java.math.BigDecimal;

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
public class AccountRequest {
	
	@NotNull(message = "User ID cannot be null")
	private Long userId;
	
	@NotBlank(message = "Account type is required")
	private String accountType;
	
	@NotNull(message = "Balance is required")
	@DecimalMin(value = "0.0", inclusive = true, message = "Balance cannot be negative")
	private BigDecimal balance;
		
	
}
