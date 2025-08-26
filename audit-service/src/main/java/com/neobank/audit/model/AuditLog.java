package com.neobank.audit.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "audit_logs")
public class AuditLog {
	
	@Id
	private String id;
	
    private String entity;       // e.g., "Account", "Transaction"
    private String entityId;     // e.g., accountNumber, transactionId
    private String action;       // e.g., "DEPOSIT", "WITHDRAWAL"
    private String performedBy;  // userId or username
    private BigDecimal  amount;		// amount
    private Instant timestamp;   // when the action happened
    private String details;      // JSON/text with extra info
    
	
	
	
	

}
