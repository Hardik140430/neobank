package com.neobank.reports.kafka;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.neobank.reports.dto.TransactionEvent;
import com.neobank.reports.entity.RawTransactionEvent;
import com.neobank.reports.repository.RawTransactionEventRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionEventListener {

	private final RawTransactionEventRepository repository;
	
	
	// topic name 'transaction-events' assumed. Adjust if different.
	@KafkaListener(topics = "transaction-events", groupId = "${kafka.consumer.group-id}")
	public void onTransactionEvent(TransactionEvent event) {
		// convert incoming event to RawTransactionEvent and persist
		RawTransactionEvent raw = RawTransactionEvent.builder()
				.eventId(UUID.randomUUID())
				.transactionId(event.getTransactionId())
				.accountId(event.getAccountId())
				.fromAccountNumber(event.getFromAccountNumber())
				.toAccountNumber(event.getToAccountNumber())
				.amount(event.getAmount())
				.transactionType(event.getTransactionType())
				.status(event.getStatus())
				.occuredAt(event.getOccuredAt() == null ? OffsetDateTime.now() : event.getOccuredAt())
				.rawPayload(event.toString())
				.build();
		
		repository.save(raw);
	}
}
