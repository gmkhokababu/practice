package com.jwt.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.project.entity.UserAccount;
import com.jwt.project.service.UserService;

@RestController
public class HelloController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	public String sayHello() {
		return "আসসালামু আলাইকুম প্রোগ্রাম রান করেছে।"; 
	}
	
	@PostMapping("/add-user")
	public UserAccount adduser(@RequestBody UserAccount user) {
		return userService.saveUser(user);
	}

}
