package com.neobank.audit.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.neobank.audit.model.AuditLog;

public interface AuditLogRepository extends MongoRepository<AuditLog, String> {
	// find logs by entity type
	List<AuditLog> findByEntity(String entity);
	
	// find logs for a specific entityId (e.g., accountNumber, transactionId)
	List<AuditLog> findByEntityId(String entityId);
	
}
