package com.welcome.bot.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.welcome.bot.domain.Audit;

@Component
public class AuditNotificationSchedulerService {
	
	@Autowired
	AuditService auditService;

	@Scheduled(cron = "0 0/2 * * * ?")
	public void scheduleCheckNewAuditNotifications() {
		// TEST
		System.out.println("SCHEDULED NOTIFICATION CRON - " + new Date());
		System.out.println("SCHEDULED CRON | LOG COUNT IS " + AuditService.NEW_LOGS_COUNT);
		// TEST ABOVE
		
		
		List<Audit> newAuditList = auditService.getAllNotSeen();
		if(!newAuditList.isEmpty()) {
			int count = newAuditList.size();
			AuditService.NEW_LOGS_COUNT += count;
		}
		
		// TEST
//		AuditService.NEW_LOGS_COUNT++;
	}
}