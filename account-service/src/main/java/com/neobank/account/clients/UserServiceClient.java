package com.neobank.account.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.neobank.account.dto.AccountRequest;
import com.neobank.account.dto.User;


//✅ This tells Feign to look up USER-SERVICE from Eureka and call its endpoints
@FeignClient(name = "user-service") 
public interface UserServiceClient {
	
	// ✅ This will call the endpoint in user-service that check the user
	@GetMapping("/user/{id}")
	ResponseEntity<User> getUserById(@PathVariable Long id);
		

}
