package com.neobank.user.dto;

import lombok.Data;

@Data
public class UserRequest {

	private String fullName;
	private String email;
	private String mobile;
	private String address;	
}
