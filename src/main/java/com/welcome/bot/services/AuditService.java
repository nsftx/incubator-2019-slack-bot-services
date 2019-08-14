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
	
	public void createScheduleLog(List<Schedule> scheduleList, String channel) {
		String entityInfo = "deleted";
		String channelInfo = "deleted";
		String entity = "Schedule";
		
		for (Schedule schedule : scheduleList) {
			Integer entityId = schedule.getScheduleId();
			Audit audit = new Audit(channel, channelInfo, entityId, entity, entityInfo);	 
			auditRepository.save(audit);
		}
	}
	
	public void createTriggerLog(List<Trigger> triggerList, String channel) {
		String entityInfo = "deleted";
		String channelInfo = "deleted";
		String entity = "Trigger";
		
		for (Trigger trigger : triggerList) {
			Integer entityId = trigger.getTriggerId();
			Audit audit = new Audit(channel, channelInfo, entityId, entity, entityInfo);	 
			auditRepository.save(audit);
		}
	}




}
