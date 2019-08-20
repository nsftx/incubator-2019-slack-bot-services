package com.welcome.bot.slack.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.welcome.bot.slack.api.customexceptionhandler.InvalidArgumentException;
import com.welcome.bot.slack.api.customexceptionhandler.MessageSendingException;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;
import com.welcome.bot.slack.api.model.messagepayload.MessagePayload;
import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;
import com.welcome.bot.slack.api.model.publishevent.PublishInteractionMessage;
import com.welcome.bot.slack.api.utils.ConnectionGenerator;
import com.welcome.bot.slack.api.utils.MessageSender;
import com.welcome.bot.slack.api.utils.PayloadGenerator;

@Service
public class SlackClientService implements SlackClientApi {
	private ConnectionGenerator connectionGen;
	private PayloadGenerator payloadGen;
	private MessageSender sender;
	private ObjectMapper jsonMapper;

	//Constructor
	@Autowired
	public SlackClientService(ConnectionGenerator connectionGen, PayloadGenerator payloadGen, MessageSender sender, ObjectMapper jsonMapper) {
		this.connectionGen = connectionGen;
		this.payloadGen = payloadGen;
		this.sender = sender;
		this.jsonMapper = jsonMapper;
	}

	@Override
	public void sendMessage(String channel, String text) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getMessagePayload(channel, text);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getPostMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!status) {
			throw new MessageSendingException("Message sending failed due to Slack unavailability");
		}
	}

	@Override
	public void sendMessage(String channel, String text, boolean isSmallImage) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getMessagePayload(channel, text, isSmallImage);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getPostMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!status) {
			throw new MessageSendingException("Message sending failed due to Slack unavailability");
		}
	}

	@Override
	public void sendMessage(String channel, String text, String user) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || user == null || user.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getPrivateMessagePayload(channel, text, user);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getPrivateMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!status) {
			throw new MessageSendingException("Message sending failed due to Slack unavailability");
		}
	}

	@Override
	public void sendMessage(String channel, String text, String user, boolean isSmallImage) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || user == null || user.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getPrivateMessagePayload(channel, text, user, isSmallImage);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getPrivateMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(!status) {
			throw new MessageSendingException("Message sending failed due to Slack unavailability");
		}
	}

	@Override
	public String sendMessagePoll(String channel, String text, HashMap<Integer,String> choices, UUID pollID) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || choices == null || choices.isEmpty() || pollID == null) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		String messageTimestamp = "";
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getMessagePollPayload(channel, text, choices, pollID.toString());

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			messageTimestamp = sender.sendPollGetPollTimestamp(connectionGen.getPostMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(messageTimestamp == null || messageTimestamp.isEmpty()) {
			throw new MessageSendingException("Poll sending failed due to Slack unavailability");
		}
		return messageTimestamp;
	}

	@Override
	public void updateMessage(String channel, String newText, String messageTimestamp) throws SlackApiException {
		if(channel == null || channel.isEmpty() || newText == null || newText.isEmpty() || messageTimestamp == null || messageTimestamp.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getMessageUpdatePayload(channel, newText, messageTimestamp);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getMessageUpdateConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(status == false) {
			throw new MessageSendingException("Message updating failed due to Slack unavailability");
		}
	}

	@Override
	public void deleteMessage(String channel, String messageTimestamp) throws SlackApiException {
		if(channel == null || channel.isEmpty() || messageTimestamp == null || messageTimestamp.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getMessageDeletePayload(channel, messageTimestamp);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			status = sender.sendMessageGetStatus(connectionGen.getMessageDeleteConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(status == false) {
			throw new MessageSendingException("Message deleting failed due to Slack unavailability");
		}
	}

	@Override
	public String createSchedule(String channel, String text, Date postAt) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		String scheduleID = null;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		payload = payloadGen.getSchedulePayload(channel, text, postAt);

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			scheduleID = sender.sendScheduleGetScheduleId(connectionGen.getScheduleMsgConnection(), payloadJSON);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(scheduleID == null) {
			throw new MessageSendingException("Schedule creating failed due to Slack unavailability");
		}
		return scheduleID;
	}

	@Override
	public String createSchedule(String channel, String text, Date postAt, String repeatInterval) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null || repeatInterval == null) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		String scheduleID = null;
		String payloadJSON = "";
		List<MessagePayload> payloads = payloadGen.getScheduleIntervalPayload(channel, text, postAt, repeatInterval);

		StringBuilder sb = new StringBuilder();
		String id = "";
		for(MessagePayload payload : payloads) {
			try {
				payloadJSON = jsonMapper.writeValueAsString(payload);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			try {
				id = sender.sendScheduleGetScheduleId(connectionGen.getScheduleMsgConnection(), payloadJSON);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(id != null) {
				sb.append(id);
				sb.append(",");
			}
		}
		if(sb != null) {
			sb = sb.deleteCharAt(sb.length()-1);
			scheduleID = sb.toString();
		}

		if(scheduleID == null) {
			throw new MessageSendingException("Schedule creating failed due to Slack unavailability");
		}

		return scheduleID;
	}

	@Override
	public void deleteSchedule(String channel, String scheduleID) throws SlackApiException {
		if(scheduleID == null || scheduleID.isEmpty() || channel == null || channel.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload;
		String payloadJSON = "";

		String[] allScheduleIDs = scheduleID.split(",");
		List<Boolean> allStatuses = new ArrayList<>();
		for(String schID : allScheduleIDs) {
			payload = new MessagePayload();
			payload = payloadGen.getScheduleDeletePayload(channel, schID);
			try {
				payloadJSON = jsonMapper.writeValueAsString(payload);
				status = sender.sendMessageGetStatus(connectionGen.getDeleteScheduledMsgConnection(), payloadJSON);
				allStatuses.add(status);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for(boolean stat : allStatuses) {
			if(stat == false) {
				throw new MessageSendingException("Schedule deleting failed due to Slack unavailability");
			}
		}
	}

	@Override
	public List<Channel> getChannelsList() {
		List<Channel> allChannels = new ArrayList<>();
		try {
			allChannels = sender.sendRequestToGetChannelsList(connectionGen.getChannelsListConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allChannels;
	}

	public void deleteAllSchedulesFromSlack() {
		List<String> allSchedules = new ArrayList<>();
		try {
			allSchedules = sender.sendRequestToGetSchedulesList(connectionGen.getSchedulesListConnection());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String s : allSchedules) {
			try {
				deleteSchedule(s,"#general");
			} catch (SlackApiException e) {
				e.printStackTrace();
			}
		}
	}

	//TODO - TEST/DELETE


//	@EventListener
//	public void handleEvent(SlackEventTriggeredEvent event) {
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
//		try {
//			sendMessagePoll(channel, "poll message", choices, id);
//		} catch (SlackApiException e) {
//			e.printStackTrace();
//		}
//
//		//		sendMessage(channel, "public message");
//		//		createSchedule(channel, "repeating schedule", new Date(), "weekly");
//	}
//
//	//TODO - TEST/DELETE
//	@EventListener
//	public void handleInteraction(SlackInteractionTriggeredEvent interaction) {
//		PublishInteractionMessage interactionData = interaction.getInteractionData();
//
//		String channel = interactionData.getChannel();
//		String text = interactionData.getText();
//		String user = interactionData.getUser();
//		String choice = interactionData.getChoiceSelected();
//		String choiceID = interactionData.getChoiceID();
//		String pollID = interactionData.getPollID().toString();
//
//		String timestamp = interactionData.getTimestamp();
//		System.out.println("IN HANDLE INTERACTION, INTERACTION TS (TIMESTAMP) IS : " + timestamp);
//
//		String combinedResponse = "RESULT:\nText: " + text + "\nUser: " + user + "\nChoice Selected: " + choice + "\nChoice ID: " + choiceID + "\nBlock ID: " + pollID;
//		try {
//			//			sendMessage(channel, combinedResponse, user);
//			updateMessage(channel, combinedResponse, timestamp);
//		} catch (SlackApiException e) {
//			e.printStackTrace();
//		}
//	}

}
