package com.welcome.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import com.welcome.bot.domain.Audit;
import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.services.AuditService;

public class AuditController {

	@Autowired
	AuditService auditService;
	
	@GetMapping("/api/logs")
	public Page<AuditDTO> getAllLogs(Pageable pageable){
		return auditService.getAllLogs(pageable);
	}
	
}
