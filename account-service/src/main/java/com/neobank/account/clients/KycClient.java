package com.neobank.account.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.neobank.account.dto.KycStatusResponse;

@FeignClient(name = "kyc-service" )
public interface KycClient {
	
	@GetMapping("/kyc/status/{userId}")
	KycStatusResponse getKycStatus(@PathVariable Long userId);
}
