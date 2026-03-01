package com.jwt.security.config;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${app.jwt.secret}")
	private String jwtSecret;
	
	@Value("${app.jwt.expiration}")
	private int jwtExpirationMs;
	
	
	//Token build method
	
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime()+jwtExpirationMs)).signWith(key(), SignatureAlgorithm.HS256).compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	//User name read method from Tokens
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}
	
	// Token right or wrong checking method
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		}catch(Exception e) {
			System.out.println("Invalid Jwt: "+ e.getMessage());
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}








