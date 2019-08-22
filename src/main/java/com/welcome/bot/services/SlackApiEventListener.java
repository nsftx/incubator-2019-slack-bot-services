package com.welcome.bot.services;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.welcome.bot.slack.api.EventType;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.SlackEventTriggeredEvent;
import com.welcome.bot.slack.api.SlackInteractionTriggeredEvent;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
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
		Integer choiceId = Integer.parseInt(interactionData.getChoiceID());
		UUID pollId = interactionData.getPollID();
		
		String timestamp = interactionData.getTimestamp();
		System.out.println("IN HANDLE INTERACTION, INTERACTION TS (TIMESTAMP) IS : " + timestamp);
		
		String combinedResponse = "RESULT:\nText: " + text + "\nUser: " + user + "\nChoice Selected: " + choice + "\nChoice ID: " + choiceId + "\nBlock ID: " + pollId;
		
		slackService.createAVote(user, choiceId, pollId);
		
		try {
			slackClientApi.sendMessage(channel, combinedResponse, user);
		} catch (SlackApiException e) {
			e.printStackTrace();
		}
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
			System.out.println(eventType + " " + channel);
			slackService.triggerApp(eventType, channel);
			break;
		case "member_joined_channel":
		case "member_left_channel":
			//slackService.triggerApp(eventType, channel, user);
			break;
		case "channel_deleted":
			slackService.logChannelActivities(eventData);
			break;
		default:
			break;			
			
		}	
		
			
//		app_mention,
//		member_joined_channel,
//		member_left_channel,
//		channel_created,
//		channel_deleted,
//		channel_rename,
//		channel_archive,
//		channel_unarchive
	
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
