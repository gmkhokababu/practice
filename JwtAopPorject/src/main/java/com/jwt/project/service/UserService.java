package com.jwt.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.project.entity.UserAccount;
import com.jwt.project.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public UserAccount saveUser(UserAccount user) {
		return userRepository.save(user);
	}
}
