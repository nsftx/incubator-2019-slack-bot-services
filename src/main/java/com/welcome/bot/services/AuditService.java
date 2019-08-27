package com.welcome.bot.services;

import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.welcome.bot.domain.Audit;
import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.exception.user.UserNotFoundException;
import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.repository.AuditRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.UserPrincipal;

@Service
public class AuditService {

	
	AuditRepository auditRepository;
	
	ModelMapper modelMapper;
	
	UserRepository userRepository;
	
	public static int NEW_LOGS_COUNT = 0;
	
	@Autowired
	public AuditService(AuditRepository auditScheduleRepository,
					ModelMapper modelMapper,
					UserRepository userRepository) {
		this.auditRepository = auditScheduleRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

	public Page<AuditDTO> getAllLogs(Pageable pageable, UserPrincipal userPrincipal) {

		
		//UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new UserNotFoundException(userPrincipal.getId()));
		
		Page<Audit> auditPage = null;
		
		Collection<? extends GrantedAuthority> autorities = userPrincipal.getAuthorities();
		
		if(autorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			auditPage = auditRepository.findAll(pageable);
		}
		else if(autorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			auditPage = auditRepository.findAllByUser(pageable, user);
		}
		
		List<Audit> auditList = auditPage.getContent();
		
		List<AuditDTO> auditDtoList = modelMapper.map(auditList, new TypeToken<List<AuditDTO>>(){}.getType());
		
		Page<AuditDTO> auditDtoPage = new PageImpl<AuditDTO>(auditDtoList, pageable, auditPage.getTotalElements());

		updateSeenStatus(auditList);
		NEW_LOGS_COUNT = 0;
		
		return auditDtoPage;
	}
	
	private void updateSeenStatus(List<Audit> auditList) {
		for (Audit audit : auditList) {
			if(!audit.getSeen()) {
				audit.setSeen();
				auditRepository.save(audit);
			}
		}	
	}
	
	//notification method
	public List<Audit> getAllNotSeen(){
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user = userRepository.findById(principal.getId())
				.orElseThrow(() -> new UserNotFoundException(principal.getId()));
		
		List<Audit> notSeenList = auditRepository.findAllByUserAndSeen(user, false);
		
		return notSeenList;
	}
	
	//notification method
	public List<Audit> getAllSeen(){
		UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		User user = userRepository.findById(principal.getId())
				.orElseThrow(() -> new UserNotFoundException(principal.getId()));
		
		List<Audit> seenList = auditRepository.findAllByUserAndSeen(user, true);
		
		return seenList;
	}
	
	@Transactional(propagation = Propagation.MANDATORY)
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
	
	@Transactional(propagation = Propagation.MANDATORY)
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
