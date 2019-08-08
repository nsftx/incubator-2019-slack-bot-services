package com.welcome.bot.services;


import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.SlackEventTriggeredEvent;

@Component
public class SlackService implements ApplicationListener<SlackEventTriggeredEvent>{

	@Autowired
	SlackClientApi slackClientApi; 
	
	@Autowired 
	MessageService messageService;
	
	@Autowired
	ScheduleService scheduleService;

	@Autowired
	TriggerService triggerService;
	
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	AuditService auditService;
	
	@Override
	public void onApplicationEvent(SlackEventTriggeredEvent event) {
		HashMap<String, String> eventData = event.getEventData();

		switch (eventData.get("triggerType")) {
		case "app_mention":
			triggerApp(eventData);
			break;
		case "channel_deleted":
		case "channel_archived":
			logChannelActivities(eventData);
		default:
			break;
		}		
		
		System.out.println("--------------------------");
		System.out.println(slackClientApi.getChannelsList());
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
			auditService.createLog(scheduleList, channel, eventData.get("triggerType"));
			//auditService.createLog(triggerList, channel, eventData.get("triggerType"));
			
			//delete all scheduled and triggers
			triggerService.deleteAllTriggersByList(triggerList);
			
		}else if(triggerType == "channel_archived") {
			triggerService.updateActiveStatus(triggerList, false);
			scheduleService.updateActiveStatus(scheduleList, false);
			
			auditService.createLog(scheduleList, channel, eventData.get("triggerType"));
		}
		
		
		//auditService.createLogForDeletedChannel(triggerList, channel);

		
	}

//	public JSONObject getChannelsList() {
//
//		//return slackClientApi.getChannelsList();
//	}

}
