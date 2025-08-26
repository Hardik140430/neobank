package com.neobank.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neobank.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
}
