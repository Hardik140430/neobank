package com.neobank.account.events;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedEvent {

	private String accountNumber;
	private Long userId;
	private String accountType;
	private BigDecimal balance;
	private Instant createdAt;
	
}
