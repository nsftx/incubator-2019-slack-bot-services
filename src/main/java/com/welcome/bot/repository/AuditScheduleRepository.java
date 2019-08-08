package com.welcome.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.welcome.bot.domain.AuditSchedule;

public interface AuditScheduleRepository extends JpaRepository<AuditSchedule, Integer>{
	
}
