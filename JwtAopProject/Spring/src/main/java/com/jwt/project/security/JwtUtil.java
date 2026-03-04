package com.jwt.project.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	//This is my secret key
	private String secret= "mySecretKey007mySecretKey007mySecretKey00";
	
	
	//Token create method
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*10))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
	
	// get username 
	public String getUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
