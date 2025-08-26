package com.neobank.account.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "audit_logs")
public class AuditLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String entity; 	 // e.g., "Account", "Transaction"
	
	private String entityId; // e.g., accountNumber, transactionId
	
	private String action;	// e.g., "CREATE", "UPDATE"
	
	private String performedBy;	// userId or username who performed the action
	
	private Instant timestamp;	// when the action happened
	
	@Column(length = 1000)
	private String details;		// when the action happened
	

}
