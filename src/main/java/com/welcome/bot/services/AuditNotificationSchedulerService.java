package com.welcome.bot.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.welcome.bot.controllers.AuditController;
import com.welcome.bot.domain.Audit;

@Component
public class AuditNotificationSchedulerService {
	
	@Autowired
	AuditService auditService;

	@Scheduled(cron = "0 0/5 * * * ?")
	public void scheduleCheckNewAuditNotifications() {
		// TEST
		System.out.println("SCHEDULED NOTIFICATION CRON - " + new Date());
		
//		List<Audit> newAuditList = auditService.getAllLogsWhereSeenFalse();
//		if(!newAuditList.isEmpty()) {
//			int count = newAuditList.size();
//			AuditController.NEW_LOGS_COUNT += count;
//		}
	}
}