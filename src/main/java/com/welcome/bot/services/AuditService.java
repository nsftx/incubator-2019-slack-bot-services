package com.welcome.bot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.AuditSchedule;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.repository.AuditScheduleRepository;

@Service
public class AuditService {

	
	AuditScheduleRepository auditScheduleRepository;
	
	@Autowired
	public AuditService(AuditScheduleRepository auditScheduleRepository) {
		this.auditScheduleRepository = auditScheduleRepository;
	}

	public void createLog(List<Schedule> scheduleList, String channel, String event) {
		AuditSchedule audit = null;
		String scheduleInfo;
		String channelInfo;
		if(event == "channel_deleted") {
			scheduleInfo = "deleted";	
			channelInfo = "deleted";
		}else {
			scheduleInfo = "deativated";
			channelInfo = "archived";
		}
		
		for (Schedule schedule : scheduleList) {
			audit = new AuditSchedule(channel, channelInfo, schedule.getScheduleId(), scheduleInfo);	 
			auditScheduleRepository.save(audit);
		}
	}


}
