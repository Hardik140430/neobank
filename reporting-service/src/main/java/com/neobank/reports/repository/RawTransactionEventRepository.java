package com.neobank.reports.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.reports.entity.RawTransactionEvent;

public interface RawTransactionEventRepository extends JpaRepository<RawTransactionEvent, Long> {
	List<RawTransactionEvent> findByAccountIdAndOccuredAtBetween(String accountId, OffsetDateTime from, OffsetDateTime to);
	List<RawTransactionEvent> findByOccuredAtBetween(OffsetDateTime from, OffsetDateTime to);
}
