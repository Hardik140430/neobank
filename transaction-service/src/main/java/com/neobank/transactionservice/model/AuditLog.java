package com.neobank.transactionservice.model;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_audit_logs")
public class AuditLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String transactionId;
    private String fromAccountNumber;     // affected account
    private String toAccountNumber;
    private BigDecimal amount;        // amount
    
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;   //DEPOSIT/WITHDRAWL
    private String description;       // optional
    private Instant createdAt;        // timestamp
	

}
