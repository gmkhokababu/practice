package com.jwt.project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public String sayHello() {
		return "আসসালামু আলাইকুম প্রোগ্রাম রান করেছে।"; 
	}

}
