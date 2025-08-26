package com.neobank.audit.kafka;

import java.time.Instant;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.neobank.audit.events.TransactionEvent;
import com.neobank.audit.model.AuditLog;
import com.neobank.audit.repository.AuditLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventListener {

	private final AuditLogRepository repository;

	@KafkaListener(topics = "${kafka.topics.transaction}", groupId = "audit-service")
	public void onTransactionEvent(TransactionEvent event) {
		
		log.info("🚀 TransactionEventListener triggered...");
		
		AuditLog auditLog = AuditLog.builder()
				.entity("Transaction")
				.entityId(event.getTransactionId().toString())
				.action(event.getTransactionType())
				.amount(event.getAmount())
				.performedBy("System")
				.timestamp(Instant.now())
				.details("Amount: " + event.getAmount() + ", From: " 
                        + event.getFromAccountNumber() + ", To: " 
                        + event.getToAccountNumber())
				.build();
		System.out.println("innnnnnnnnn "+ auditLog);
		repository.save(auditLog);
		log.info("✅ Audit log saved for transactionId={}", event.getTransactionId());
	}

}
