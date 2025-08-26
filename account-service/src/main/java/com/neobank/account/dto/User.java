package com.neobank.account.dto;

import lombok.Data;

@Data
public class User {
	
	private Long id;
	
	private String fullName;
	
	private String email;
	
	private String mobile;
	
	private String address;
}
