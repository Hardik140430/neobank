package com.neobank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.account.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
