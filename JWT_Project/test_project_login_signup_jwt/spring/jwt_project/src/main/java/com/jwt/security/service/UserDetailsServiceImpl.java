package com.jwt.security.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.jwt.security.entity.User;
import com.jwt.security.repository.UserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found "+username));
		
		
		// ইউজার রোলে 'ROLE_' প্রিফিক্স যোগ করে অথরিটি তৈরি করা
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				user.getRoles().stream()
				.map(role -> 
					new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
					.collect(Collectors.toList()));
		
		
		
	}

}
