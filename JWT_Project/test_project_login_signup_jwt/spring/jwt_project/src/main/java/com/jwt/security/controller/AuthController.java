package com.jwt.security.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.config.JwtUtil;
import com.jwt.security.entity.Role;
import com.jwt.security.entity.User;
import com.jwt.security.payload.LoginRequest;
import com.jwt.security.payload.SignupRequest;
import com.jwt.security.repository.RoleRepo;
import com.jwt.security.repository.UserRepo;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepo;

	@Autowired
	RoleRepo roleRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	// ১. লগইন API
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		System.out.println("signin method call");
		// ইউজারনেম ও পাসওয়ার্ড ভেরিফাই করা
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		org.springframework.security.core.userdetails.UserDetails userDetails = 
	            (org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal();

		// টোকেন জেনারেট করা
		String jwt = jwtUtil.generateToken(authentication.getName());
		
		System.out.println("signin method call authnetication: "+authentication);
		System.out.println("signin method call jwt: "+jwt);
		Map<String, Object> response = new HashMap<>();
		response.put("accessToken", jwt);
		response.put("username", userDetails.getUsername());
//		response.put("email", userDetails.getEmail());
		response.put("roles", userDetails.getAuthorities());
		
		User user= userRepo.findByUsername(userDetails.getUsername()).get();
		response.put("email", user.getEmail());

		return ResponseEntity.ok(response);
	}

	
	// ২. রেজিস্ট্রেশন API
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest){
		if(userRepo.findByUsername(signupRequest.getUsername()).isPresent()) {
			return ResponseEntity.badRequest().body("Error: Username is already taken!");
		}
		
		// নতুন ইউজার তৈরি
		User user = new User();
		user.setUsername(signupRequest.getUsername());
		user.setEmail(signupRequest.getEmail());
		user.setPassword(encoder.encode(signupRequest.getPassword()));
		
		// রোল সেট করা
		Set<String> strRoles=signupRequest.getRole();
		
		Set<Role> roles=new HashSet<>();
		
		if(strRoles==null) {
			Role userRole = roleRepo.findByName("ROLE_USER")
					.orElseThrow(()->new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}else {
			strRoles.forEach(role ->{
				switch (role) {
				case "admin": 
					Role adminRole = roleRepo.findByName("ROLE_ADMIN")
							.orElseThrow(()-> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
					
				default:
					Role userRole = roleRepo.findByName("ROLE_USER")
						.orElseThrow(()-> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
					
				}
			});
		}
		
		
		user.setRoles(roles);
		userRepo.save(user);
		
		
		return ResponseEntity.ok("Registration successfull!");
		
		
		
	}
}
