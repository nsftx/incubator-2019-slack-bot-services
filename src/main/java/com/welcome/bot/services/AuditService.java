package com.welcome.bot.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Audit;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.repository.AuditRepository;

@Service
public class AuditService {

	
	AuditRepository auditRepository;
	
	ModelMapper modelMapper;
	
	@Autowired
	public AuditService(AuditRepository auditScheduleRepository, ModelMapper modelMapper) {
		this.auditRepository = auditScheduleRepository;
		this.modelMapper = modelMapper;
	}

	public Page<AuditDTO> getAllLogs(Pageable pageable) {
		Page<Audit> auditPage = auditRepository.findAll(pageable);
		List<Audit> auditList = auditPage.getContent();
		
		List<AuditDTO> auditDtoList = modelMapper.map(auditList, new TypeToken<List<AuditDTO>>(){}.getType());
		Page<AuditDTO> auditDtoPage = new PageImpl<AuditDTO>(auditDtoList, pageable, auditPage.getTotalElements());
		return auditDtoPage;	
	}
	
	public void createScheduleLog(List<Schedule> scheduleList) {
		String channelName = scheduleList.get(0).getChannel();
		String cause = "because " + channelName + "is deleted";
	
		for (Schedule schedule : scheduleList) {
			String consequence = "Schedule with id: " + schedule.getScheduleId() + "and text: " + schedule.getMessage().getText() + "is deleted";
			Audit audit = new Audit(cause, consequence);	 
			auditRepository.save(audit);
		}
	}
	
	public void createTriggerLog(List<Trigger> triggerList) {
		String channelName = triggerList.get(0).getChannel();
		String cause = "because " + channelName + "is deleted";
		for (Trigger trigger : triggerList) {
			String consequence = "Trigger with id: " + trigger.getTriggerId() + "and text: " + trigger.getMessage().getText() + "is deleted";
			Audit audit = new Audit(cause, consequence);	 
			auditRepository.save(audit);
		}
	}
}
