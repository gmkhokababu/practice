package com.jwt.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.project.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.disable()) //csrf অফ করে দিলাম যেন স্প্রিং সিকিউরিটি ঝামেলা না করে।
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/login","/add-user").permitAll() //এই দুটো সবার জন্য খোলা
					.anyRequest().authenticated() // সব রিকুয়েস্ট টোকেন লাগবে।
					)
					.sessionManagement(session -> session
							.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // সেশন সেভ হবে না
							)
					// ইউজারনেম-পাসওয়ার্ড ফিল্টারের আগে আমাদের JWT ফিল্টার বসবে
					.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 
		return http.build();
	} 
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
