package com.welcome.bot.services;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.welcome.bot.slack.api.EventType;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.SlackEventTriggeredEvent;
import com.welcome.bot.slack.api.SlackInteractionTriggeredEvent;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;
import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;
import com.welcome.bot.slack.api.model.publishevent.PublishInteractionMessage;

@Component
public class SlackApiEventListener {

	SlackClientApi slackClientApi; 
	
	SlackService slackService;
	
	@Autowired	
	public SlackApiEventListener(final SlackClientApi slackClientApi, final SlackService slackService) {
		this.slackClientApi = slackClientApi;
		this.slackService = slackService;
	}


//	@EventListener
//	public void handleEvent(SlackEventTriggeredEvent event) {
//		//HashMap<String, String> eventData = event.getEventData();
//		System.out.println(event.toString());
//		
//		PublishEventMessage eventData = event.getEventData();
//		String channel = eventData.getChannel();
//		String eventType = eventData.getEventType().toString();
//		switch (eventType.toString()) {
//		case "app_mention":
//			slackService.triggerApp(eventType, channel);
//			break;
//		case "channel_deleted":
//			slackService.logChannelActivities(eventData);
//			break;
//		default:
//			break;
//		}		
//
//		System.out.println(slackClientApi.getChannelsList());
//	}
	
	@EventListener
	public void handleInteraction(SlackInteractionTriggeredEvent interaction) {
		PublishInteractionMessage interactionData = interaction.getInteractionData();
		
		String channel = interactionData.getChannel();
		String text = interactionData.getText();
		String user = interactionData.getUser();
		String choice = interactionData.getChoiceSelected();
		String choiceID = interactionData.getChoiceID();
		String pollID = interactionData.getPollID().toString();
		
		String timestamp = interactionData.getTimestamp();
		System.out.println("IN HANDLE INTERACTION, INTERACTION TS (TIMESTAMP) IS : " + timestamp);
		
		String combinedResponse = "RESULT:\nText: " + text + "\nUser: " + user + "\nChoice Selected: " + choice + "\nChoice ID: " + choiceID + "\nBlock ID: " + pollID;
		
		//slackService.createAVote();
		//sendMessage(channel, combinedResponse, user);
		//updateMessage(channel, combinedResponse, timestamp);
	}
	
	@EventListener
	public void handleEvent(SlackEventTriggeredEvent event) {
		
		System.out.println(event.toString());		
		
		PublishEventMessage eventData = event.getEventData();
		String channel = eventData.getChannel();
		String user = eventData.getUser();
		String eventType = eventData.getEventType().toString();
		switch (eventType.toString()) {
		
		case "app_mention":
			slackService.triggerApp(eventType, channel);
			break;
		case "member_joined_channel":
		case "member_left_channel":
			slackService.triggerApp(eventType, channel, user);
			break;
		case "channel_deleted":
			slackService.logChannelActivities(eventData);
			break;
		default:
			break;			
			
		}	
		System.out.println(slackClientApi.getChannelsList());
//		PublishEventMessage eventData = event.getEventData();
//		
//		String channel = eventData.getChannel();
//		UUID id = UUID.randomUUID();
//		HashMap<Integer,String> choices = new HashMap<Integer,String>();
//		choices.put(1, "Puppet");
//		choices.put(2, "Iron Man");
//		choices.put(3, "Deadpool");
//		choices.put(4, "Puppet (yes, puppet again)");
//		
//		String response = sendMessagePoll(channel, "poll message", choices, id);
//		System.out.println("IN HANDLE EVENT, RESPONSE (TIMESTAMP) IS: " + response);
//		
//		sendMessage(channel, "public message");
//		createSchedule(channel, "repeating schedule", new Date(), "weekly");
	}
	

}
