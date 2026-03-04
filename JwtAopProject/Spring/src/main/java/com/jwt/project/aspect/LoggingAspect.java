package com.jwt.project.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.jwt.project.util.LogUtil;

@Aspect
@Component
public class LoggingAspect {
	
	@Autowired
	private LogUtil logUtil;
	
	@Before("execution(* com.jwt.project.service.UserService.*(..))")
	public void loginBefore() {
		System.out.println("AOP বলছেঃ একটি ম্যাথড শুরু হতে যাচ্ছ...");
	} 
	
	@Before("execution(* com.jwt.project.controller.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		
		//which method are called detected that
		String methodName = joinPoint.getSignature().getName();
		
		//detect present user name
		String currentUsername = "Guest";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated()) {
			currentUsername=authentication.getName();
		}
		logUtil.saveLog(currentUsername, "Called Method: "+methodName);
		System.out.println("AOP Log: User '"+currentUsername+"' called "+methodName);
	}

}
