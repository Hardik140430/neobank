package com.neobank.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.neobank.auth.config.CustomUserDetails;
import com.neobank.auth.model.User;
import com.neobank.auth.repository.UserRepository;

@Service // Tells Spring this is a service class
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	// This method is called by Spring Security during login to fetch the user details
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Look up user in database
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return new CustomUserDetails(user);
	}

}
