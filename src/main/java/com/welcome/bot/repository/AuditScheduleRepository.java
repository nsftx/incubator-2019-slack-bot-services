package com.welcome.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.welcome.bot.domain.AuditLog;

public interface AuditScheduleRepository extends JpaRepository<AuditLog, Integer>{
	
}
