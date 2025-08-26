package com.neobank.kyc.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.kyc.dto.KycStatusResponse;
import com.neobank.kyc.entity.Kyc;
import com.neobank.kyc.repository.KycRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kyc")
@RequiredArgsConstructor
public class KycController {

	private final KycRepository kycRepository;

	@PostMapping("/submit")
	public ResponseEntity<String> submitKyc(@RequestBody Kyc kyc) {
		kyc.setVerified(false);
		kycRepository.save(kyc);
		return ResponseEntity.ok("KYC Submitted successfully, Pending verification");
	}

	@GetMapping("/status/{userId}")
	public ResponseEntity<KycStatusResponse> checkStatus(@PathVariable Long userId) {
		Optional<Kyc> kyc = kycRepository.findByUserId(userId);
		if (kyc.isEmpty()) {
			return ResponseEntity.ok(new KycStatusResponse(userId, false, "KYC not found"));
		}

		boolean isVerfied = kyc.get().isVerified();
		String message = isVerfied ? "KYC Verified" : "KYC Verification Pending";

		return ResponseEntity.ok(new KycStatusResponse(userId, isVerfied, message));

	}

	@PutMapping("/verify/{userId}")
	public ResponseEntity<String> verifyKyc(@PathVariable Long userId) {
		Optional<Kyc> kyc = kycRepository.findByUserId(userId);
		if (kyc.isEmpty()) {
			return ResponseEntity.ok("KYC not found.");
		}

		Kyc kycData = kyc.get();
		kycData.setVerified(true);
		kycRepository.save(kycData);
		return ResponseEntity.ok("KYC Verified successfully");
	}

}
