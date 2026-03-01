package com.jwt.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jwt.security.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity // এটি দিয়ে আমরা ভবিষ্যতে মেথড লেভেলে পারমিশন (AOP স্টাইলে) কন্ট্রোল করতে পারব
public class WebSecurityConfig {

	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	
	// ১. টোকেন ফিল্টারটি তৈরি করার বিন
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	// ২. পাসওয়ার্ড এনক্রিপ্ট করার জন্য BCrypt ব্যবহার করছি
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// ৩. অথেন্টিকেশন প্রোভাইডার সেটআপ
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	//৪. অথেন্টিকেশন ম্যানেজার (লগইন রিকোয়েস্ট হ্যান্ডেল করার জন্য)
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public WebMvcConfigurer crossConfigur() {
		return new WebMvcConfigurer() {
			@Override 
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:4200") //angular ports
					.allowedMethods("GET","POST","PUT","DELETE","OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}
	
	
	// ৫. আসল সিকিউরিটি রুলস (এখানেই আমরা মেইন কনফিগারেশন করি)
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf -> csrf.disable()) //JWT ব্যবহার করলে CSRF ডিজেবল রাখা যায়
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // সার্ভারে কোনো সেশন সেভ হবে না
			.authorizeHttpRequests(auth ->
					auth.requestMatchers("/api/auth/**").permitAll()// লগইন ও রেজিস্ট্রেশন URL সবার জন্য খোলা
					.requestMatchers("/api/test/all").permitAll() // public api
					.anyRequest().authenticated() // বাকি সবকিছুর জন্য টোকেন লাগবে
			);
		
		http.authenticationProvider(authenticationProvider());
		
		// আমাদের কাস্টম JWT ফিল্টারটি ডিফল্ট ফিল্টারের আগে বসিয়ে দিচ্ছি
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
	
	
	

}
