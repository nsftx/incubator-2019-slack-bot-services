package com.welcome.bot.services;


import java.util.HashMap;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
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
			slackService.logChannelActivities(eventData);
			break;
		default:
			break;
		}		

	}
	

}
