package com.neobank.account.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neobank.account.model.AuditLog;
import com.neobank.account.repository.AuditLogRepository;

@Service
public class AuditService {
	
	@Autowired
	private AuditLogRepository auditLogRepository;
	
	public void logEvent(String entity, String entityId, String action, String performedBy, String details) {
		AuditLog log = AuditLog.builder()
				.entity(entity)
				.entityId(entityId)
				.action(action)
				.performedBy(performedBy)
				.timestamp(Instant.now())
				.details(details)
				.build();
		
		auditLogRepository.save(log);
	}
}
