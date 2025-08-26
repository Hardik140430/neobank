package com.neobank.transactionservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.transactionservice.model.AuditLog;

public interface AuditRepository extends JpaRepository<AuditLog, Long>{

}
