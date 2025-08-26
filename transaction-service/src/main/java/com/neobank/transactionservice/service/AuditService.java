package com.neobank.transactionservice.service;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neobank.transactionservice.model.AuditLog;
import com.neobank.transactionservice.model.TransactionType;
import com.neobank.transactionservice.repository.AuditRepository;

@Service
public class AuditService {

	@Autowired
	private AuditRepository auditRepository;

	public void logEvent(String transactionId, String fromAccountNumber,String toAccountNumber, BigDecimal amount, TransactionType transactionType,
			String description) {
		AuditLog log = AuditLog.builder().transactionId(transactionId).fromAccountNumber(fromAccountNumber).toAccountNumber(toAccountNumber).amount(amount)
				.transactionType(transactionType).description(description).createdAt(Instant.now()).build();

		auditRepository.save(log);
	}

}
