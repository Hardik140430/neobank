package com.neobank.auth.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.neobank.auth.model.User;

//This class tells Spring Security how to get user info (username, password, roles) from our User entity
public class CustomUserDetails implements UserDetails {

	private final User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	// What roles/authorities the user has — here we hardcoded ROLE_USER
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_"+ user.getRole().name()));
	}

	// Get password for authentication
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	// Get username for authentication
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
    // Expose User entity if needed
	public User getUser() {
		return user;
	}

}
