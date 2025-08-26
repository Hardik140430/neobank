package com.neobank.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KycStatusResponse {

	private Long userId;
	private boolean verified;
	private String statusMessage;
}
