package com.jwt.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin (origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/test")

public class TestController {
	
	@GetMapping("/all")
	public String allAccess() {
		System.out.println("Call All");
		return "Public content is available for everyone.";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		System.out.println("Call User");
		return "User content can see only the login user";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		System.out.println("Call Admin");
		return "Admin content can see only then login admin";
	}
}
