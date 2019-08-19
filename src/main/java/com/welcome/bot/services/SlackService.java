package com.welcome.bot.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;

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
			try {
				slackClientApi.sendMessage(trigger.getChannel(), trigger.getMessage().getText());
			} catch (SlackApiException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void logChannelActivities(HashMap<String, String> eventData) {
		String channel = eventData.get("channel");
		List<Schedule> scheduleList = scheduleService.getAllByChannel(channel);
		List<Trigger> triggerList = triggerService.getAllByChannel(channel);

		auditService.createScheduleLog(scheduleList, channel);
		auditService.createTriggerLog(triggerList, channel);
		
		scheduleService.deleteAllSchedulesByList(scheduleList);
		triggerService.deleteAllTriggersByList(triggerList);
	}
	
	public List<Channel> getChannelsList() {
		return slackClientApi.getChannelsList();
	}	
}
