package com.welcome.bot.slack.api;

import org.springframework.context.ApplicationEvent;

import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;

@SuppressWarnings("serial")
public class SlackEventTriggeredEvent extends ApplicationEvent {

	private PublishEventMessage eventData = new PublishEventMessage();
	
	public SlackEventTriggeredEvent(Object source, PublishEventMessage eventData) {
		super(source);
		this.eventData = eventData;
	}
	
	public PublishEventMessage getEventData() {
		return eventData;
	}
}