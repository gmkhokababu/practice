package com.jwt.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.project.dto.LoginRequest;
import com.jwt.project.entity.UserAccount;
import com.jwt.project.repository.UserRepository;
import com.jwt.project.security.JwtUtil;
import com.jwt.project.service.UserService;

@RestController
public class HelloController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/hello")
	public String sayHello() {
		return "আসসালামু আলাইকুম প্রোগ্রাম রান করেছে।"; 
	}
	
	@PostMapping("/add-user")
	public UserAccount adduser(@RequestBody UserAccount user) {
		return userService.saveUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		
		// collect Data from Database.
		UserAccount user = userRepo.findByUsername(loginRequest.getUsername());
		
		//Password matching
		 if(user!=null) {
			 // check password (raw password vs encrypted password)
			 if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
				 //If match return Token
				 return jwtUtil.generateToken(user.getUsername());
			 }
		 }
		 return "Wrong username or password";
	}
			
}
