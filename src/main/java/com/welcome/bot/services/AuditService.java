package com.welcome.bot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.AuditLog;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.AuditScheduleRepository;

@Service
public class AuditService {

	
	AuditScheduleRepository auditScheduleRepository;
	
	@Autowired
	public AuditService(AuditScheduleRepository auditScheduleRepository) {
		this.auditScheduleRepository = auditScheduleRepository;
	}

	public void createScheduleLog(List<Schedule> scheduleList, String channel) {
		String entityInfo = "deleted";
		String channelInfo = "deleted";
		String entity = "Schedule";
		
		for (Schedule schedule : scheduleList) {
			Integer entityId = schedule.getScheduleId();
			AuditLog audit = new AuditLog(channel, channelInfo, entityId, entity, entityInfo);	 
			auditScheduleRepository.save(audit);
		}
	}
	
	public void createTriggerLog(List<Trigger> triggerList, String channel) {
		String entityInfo = "deleted";
		String channelInfo = "deleted";
		String entity = "Trigger";
		
		for (Trigger trigger : triggerList) {
			Integer entityId = trigger.getTriggerId();
			AuditLog audit = new AuditLog(channel, channelInfo, entityId, entity, entityInfo);	 
			auditScheduleRepository.save(audit);
		}
	}


}
