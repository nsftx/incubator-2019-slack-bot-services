package com.welcome.bot.services;


import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.google.common.base.FinalizablePhantomReference;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.SlackEventTriggeredEvent;

@Component
public class SlackApiEventListener implements ApplicationListener<SlackEventTriggeredEvent>{

	SlackClientApi slackClientApi; 
	
	SlackService slackService;
	
	@Autowired	
	public SlackApiEventListener(final SlackClientApi slackClientApi, final SlackService slackService) {
		this.slackClientApi = slackClientApi;
		this.slackService = slackService;
	}


	@Override
	public void onApplicationEvent(SlackEventTriggeredEvent event) {
		HashMap<String, String> eventData = event.getEventData();

		switch (eventData.get("triggerType")) {
		case "app_mention":
			slackService.triggerApp(eventData);
			break;
		case "channel_deleted":
		case "channel_archived":
			slackService.logChannelActivities(eventData);
		default:
			break;
		}		
		
		System.out.println("--------------------------");
		System.out.println(slackClientApi.getChannelsList());
	}
	
	

//	public JSONObject getChannelsList() {
//
//		//return slackClientApi.getChannelsList();
//	}

}
