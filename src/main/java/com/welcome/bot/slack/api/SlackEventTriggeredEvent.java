package com.welcome.bot.slack.api;

import java.util.HashMap;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class SlackEventTriggeredEvent extends ApplicationEvent {

	private HashMap<String,String> eventData = new HashMap<String,String>();
	
	public SlackEventTriggeredEvent(Object source, HashMap<String,String> eventData) {
		super(source);
		this.eventData = eventData;
	}
	
	public HashMap<String,String> getEventData(){
		return eventData;
	}
}