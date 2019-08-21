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
import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.repository.AuditRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.UserPrincipal;

@Service
public class AuditService {

	
	AuditRepository auditRepository;
	
	ModelMapper modelMapper;
	
	UserRepository userRepository;
	
	@Autowired
	public AuditService(AuditRepository auditScheduleRepository,
					ModelMapper modelMapper,
					UserRepository userRepository) {
		this.auditRepository = auditScheduleRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

	public Page<AuditDTO> getAllLogs(Pageable pageable, UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		
		Page<Audit> auditPage = null;
		
		if(user.getRole().equals("ADMIN")) {
			auditPage = auditRepository.findAll(pageable);
		}
		else if(user.getRole().equals("USER")) {
			auditPage = auditRepository.findAllByUser(pageable, user);
		}
		
		List<Audit> auditList = auditPage.getContent();
		
		List<AuditDTO> auditDtoList = modelMapper.map(auditList, new TypeToken<List<AuditDTO>>(){}.getType());
		
		System.out.println(auditDtoList);
		Page<AuditDTO> auditDtoPage = new PageImpl<AuditDTO>(auditDtoList, pageable, auditPage.getTotalElements());
		System.out.println(auditDtoPage);
		return auditDtoPage;	
	}
	
	public void createScheduleLog(List<Schedule> scheduleList) {
		String channelName = scheduleList.get(0).getChannel().substring(1);
		
		String cause = "because channel " + channelName + " is deleted";
	
		for (Schedule schedule : scheduleList) {
			String consequence = "Schedule with id: " + schedule.getScheduleId() + " and text: '" + schedule.getMessage().getText() + "' is deleted ";
			User user = schedule.getUser();
			Audit audit = new Audit(cause, consequence, user); 
			auditRepository.save(audit);
		}
	}
	
	public void createTriggerLog(List<Trigger> triggerList) {
		String channelName = triggerList.get(0).getChannel();
		String cause = "because channel " + channelName + "is deleted";
		for (Trigger trigger : triggerList) {
			String consequence = "Trigger with id: " + trigger.getTriggerId() + " and text: " + trigger.getMessage().getText() + "is deleted ";
			User user = trigger.getUser();
			Audit audit = new Audit(cause, consequence, user);	 
			auditRepository.save(audit);
		}
	}
}
