package com.jwt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.project.entity.UserAccount;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long>{

	UserAccount findByUsername(String username);
}
