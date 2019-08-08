package com.welcome.bot.slack.api;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.welcome.bot.slack.api.model.eventpayloadmodel.EventPayload;

@Component
public class SlackEventService {
	
	@Autowired
    private ApplicationEventPublisher appEventPublisher;
	
	public String handleEvent(EventPayload event) {
		if(event.getType().equals("event_callback")) {
			
			String eventType = event.getEventItem().getType();
			String user = event.getEventItem().getUser();
			String channel = event.getEventItem().getChannel();
			
			passEvent(channel, eventType, user);
		}
		else if(event.getType().equals("url_verification")) {
			return event.getChallenge();
		}
		return null;
	}
	
	private void passEvent(String channel, String eventType, String user) {
		HashMap<String, String> eventData = new HashMap<String,String>();
		eventData.put("channel", channel);
		eventData.put("triggerType", eventType);
		eventData.put("user", user);
		
		SlackEventTriggeredEvent eventHandler = new SlackEventTriggeredEvent(this, eventData);
		appEventPublisher.publishEvent(eventHandler);
	}
}