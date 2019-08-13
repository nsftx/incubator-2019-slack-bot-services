package com.welcome.bot.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.SlackClientApi;

@Service
public class SlackService {

	SlackClientApi slackClientApi; 
	 
	MessageService messageService;
	
	ScheduleService scheduleService;

	TriggerService triggerService;
	
	TriggerRepository triggerRepository;
	
	AuditService auditService;
	
	@Autowired
	public SlackService(final SlackClientApi slackClientApi,
			final MessageService messageService, 
			final ScheduleService scheduleService,
			final TriggerService triggerService,
			final TriggerRepository triggerRepository, 
			final AuditService auditService) {
		this.slackClientApi = slackClientApi;
		this.messageService = messageService;
		this.scheduleService = scheduleService;
		this.triggerService = triggerService;
		this.triggerRepository = triggerRepository;
		this.auditService = auditService;
	}

	public void triggerApp(HashMap<String, String> eventData) {
		List<Trigger> list = triggerRepository.findAllByTriggerTypeAndChannel(eventData.get("triggerType"), eventData.get("channel"));
		for (Trigger trigger : list) {
			slackClientApi.sendMessage(trigger.getChannel(), trigger.getMessage().getText());
		}
		//slackClientApi.sendMessage("#general", "Hello world!");
	}
	
	public void logChannelActivities(HashMap<String, String> eventData) {
		String channel = "#kanal-1"; //umjesto harkodiranog iskoristiti eventData
		List<Schedule> scheduleList = scheduleService.getAllByChannel(channel);
		List<Trigger> triggerList = triggerService.getAllByChannel(channel);
		
		String triggerType = eventData.get("triggerType");
		
		//ovo ce se trebati ubaciti u same servise
		if(triggerType == "channel_deleted") {
			auditService.createLog(scheduleList, channel, triggerType);
			//auditService.createLog(triggerList, channel, eventData.get("triggerType"));
			
			//delete all scheduled and triggers
			triggerService.deleteAllTriggersByList(triggerList);
			
		}else if(triggerType == "channel_archived") {
			triggerService.updateActiveStatus(triggerList, false);
			scheduleService.updateActiveStatus(scheduleList, false);
			
			auditService.createLog(scheduleList, channel, triggerType);
		}
		
		
		//auditService.createLogForDeletedChannel(triggerList, channel);

		
	}
	
}
