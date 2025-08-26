package com.neobank.kyc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.kyc.entity.Kyc;

public interface KycRepository extends JpaRepository<Kyc, Long> {

	Optional<Kyc> findByUserId(Long userId);
}
