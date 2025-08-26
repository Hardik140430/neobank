package com.neobank.reports.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionEvent {

	private String transactionId;
	private String accountId;
	private String fromAccountNumber;
	private String toAccountNumber;
	private BigDecimal amount;
	private String transactionType;
	private String status;
	private OffsetDateTime occuredAt;
}
