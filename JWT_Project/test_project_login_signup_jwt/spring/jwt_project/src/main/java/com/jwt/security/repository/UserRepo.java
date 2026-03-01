package com.jwt.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.security.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

}
