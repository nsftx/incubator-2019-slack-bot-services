package com.welcome.bot.slack.api.model.publishevent;

import com.welcome.bot.slack.api.EventType;

public class PublishEventMessage {

	private String channel;
	private EventType eventType;
	private String user;
	
	//TODO - TEST/DELETE
	private String txt;
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	//TODO - TEST/DELETE (ABOVE)

	// Constructor
	public PublishEventMessage() {}

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}