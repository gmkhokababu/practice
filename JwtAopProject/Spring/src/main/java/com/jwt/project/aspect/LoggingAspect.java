package com.jwt.project.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	@Before("execution(* com.jwt.project.service.UserService.*(..))")
	public void logBefore() {
		System.out.println("AOP বলছেঃ একটি ম্যাথড শুরু হতে যাচ্ছ...");
	} 

}
