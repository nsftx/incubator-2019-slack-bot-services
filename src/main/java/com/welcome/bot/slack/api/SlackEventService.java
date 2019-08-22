package com.welcome.bot.slack.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.welcome.bot.slack.api.model.eventpayload.EventPayload;
import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;

@Component
public class SlackEventService {

	@Autowired
	private ApplicationEventPublisher appEventPublisher;

	public String handleEvent(EventPayload event) {
		if(event.getType().equals("event_callback")) {
			String user = event.getEventItem().getUser();
			String channel = "";

			EventType eventType = null;
			switch(event.getEventItem().getType()) {
			case "app_mention":
				eventType = EventType.app_mention;
				channel = event.getEventItem().getChannelId();
				break;
			case "member_joined_channel":
				eventType = EventType.member_joined_channel;
				channel = event.getEventItem().getChannelId();
				break;
			case "member_left_channel":
				eventType = EventType.member_left_channel;
				channel = event.getEventItem().getChannelId();
				break;
			case "channel_created":
				eventType = EventType.channel_created;
				channel = event.getEventItem().getChannel().getId();
				break;
			case "channel_deleted":
				eventType = EventType.channel_deleted;
				channel = event.getEventItem().getChannelId();
				break;
			case "channel_rename":
				eventType = EventType.channel_rename;
				channel = event.getEventItem().getChannel().getId();
				break;
			case "channel_archive":
				eventType = EventType.channel_archive;
				channel = event.getEventItem().getChannelId();
				break;
			case "channel_unarchive":
				eventType = EventType.channel_unarchive;
				channel = event.getEventItem().getChannelId();
				break;
			}
			passEvent(channel, eventType, user, event.getEventItem().getText()); ////TODO - TEST/DELETE -> 4th PARAMETER
		}
		else if(event.getType().equals("url_verification")) {
			return event.getChallenge();
		}
		return null;
	}

	private void passEvent(String channel, EventType eventType, String user, String txt) { //TODO - TEST/DELETE -> 4th PARAMETER
		PublishEventMessage eventData = new PublishEventMessage();
		eventData.setChannel(channel);
		eventData.setEventType(eventType);
		eventData.setUser(user);
		
		//TODO - TEST/DELETE
		eventData.setTxt(txt);

		SlackEventTriggeredEvent eventHandler = new SlackEventTriggeredEvent(this, eventData);
		appEventPublisher.publishEvent(eventHandler);
	}
}