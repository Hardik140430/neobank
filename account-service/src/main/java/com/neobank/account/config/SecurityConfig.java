package com.neobank.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/account/**").permitAll()
						.anyRequest()
						.authenticated())
				.headers(headers -> headers.frameOptions().disable()).httpBasic(Customizer.withDefaults()); // ✅
																											// REQUIRED
		return http.build();
	}
		
}
