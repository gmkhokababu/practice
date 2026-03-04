package com.jwt.project.util;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jwt.project.entity.UserLog;
import com.jwt.project.repository.LogRepository;

@Component
public class LogUtil {

	@Autowired
	private LogRepository logRepository;
	
	public void saveLog(String username, String action) {
		UserLog log = new UserLog();
		log.setUsername(username);
		log.setAction(action);
		log.setTimestamp(LocalDateTime.now());
		logRepository.save(log);
	}
}
