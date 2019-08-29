package com.welcome.bot.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.welcome.bot.domain.Audit;

@Component
public class AuditNotificationSchedulerService {
	
	@Autowired
	AuditService auditService;

	@Scheduled(cron = "0/10 * * * * ?")
	public void scheduleCheckNewAuditNotifications() {
		if(AuditService.USER_ID != 0) {
			List<Audit> newAuditList = auditService.getAllNotSeen();
			if(!newAuditList.isEmpty()) {
				int count = newAuditList.size();
				AuditService.NEW_LOGS_COUNT += count;
			}
		}
	}
}