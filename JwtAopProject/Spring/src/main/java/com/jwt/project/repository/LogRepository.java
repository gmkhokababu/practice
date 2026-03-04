package com.jwt.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jwt.project.entity.UserLog;

@Repository
public interface LogRepository extends JpaRepository<UserLog, Long>{

}
