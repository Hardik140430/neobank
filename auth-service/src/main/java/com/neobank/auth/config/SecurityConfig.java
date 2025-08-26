package com.neobank.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.neobank.auth.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;

@Configuration // Marks this as a configuration class
@RequiredArgsConstructor // Generates constructor for final fields (we're using constructor injection)
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;

	// 🔒 This defines how authentication should be handled
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService); // Custom user lookup logic
		provider.setPasswordEncoder(passwordEncoder); // Use BCrypt for password hashing
		return provider;
	}

	// This bean provides the AuthenticationManager needed for authentication
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// 🔒 Main security filter chain - defines how requests are secured
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (enable for web apps)
				.headers(headers -> headers.frameOptions().disable()) // ALLOW H2 to load in <iframe>
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/register","/h2-console/**","/auth/login").permitAll() // Allow unauthenticated access to auth endpoints
						.requestMatchers("/auth/admin/**").hasRole("ADMIN") // only ADMIN can access
						.requestMatchers("/auth/user/**").hasAnyRole("USER", "ADMIN") // both can access						
						.anyRequest().authenticated() // All other requests require login
				).exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> response
						.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")) // Handle 401 errors
				).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No
																											// session,
																												// we're
																												// using
																												// JWT
				).authenticationProvider(authenticationProvider()); // Plug in our custom auth logic

		return http.build(); // Build and return the SecurityFilterChain
	}
	
	
}
