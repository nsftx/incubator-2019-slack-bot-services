package com.welcome.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Audit;
import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.services.AuditService;

@RestController
public class AuditController {
	
	// NOTIFICATION
	public static int NEW_LOGS_COUNT = 0;

	@Autowired
	AuditService auditService;
	
	@GetMapping("/api/logs")
	public Page<AuditDTO> getAllLogs(Pageable pageable, @CurrentUser UserPrincipal userPrincipal){
		return auditService.getAllLogs(pageable, userPrincipal);
		
		/* TODO
		 * Page<AuditDTO> auditList = auditService.getAllLogs(pageable, userPrincipal);
		 * auditService.updateAllWhereSeenFalse(set seen to true);
		 * NEW_LOGS_COUNT = 0;
		 * return auditList;
		 */
	}
	
	// NOTIFICATION
	// SSE EMIT METHOD
}