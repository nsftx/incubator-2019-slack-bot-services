package com.welcome.bot.slack.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.welcome.bot.slack.api.model.messagepayloadmodel.MessagePayload;
import com.welcome.bot.slack.api.utils.ConnectionGenerator;
import com.welcome.bot.slack.api.utils.MessageSender;
import com.welcome.bot.slack.api.utils.PayloadGenerator;

@Service
public class SlackClientService implements SlackClientApi, ApplicationListener<SlackEventTriggeredEvent> {

	private ConnectionGenerator connectionGen = new ConnectionGenerator();
	private PayloadGenerator payloadGen = new PayloadGenerator();
	private MessageSender sender = new MessageSender();
	private HttpURLConnection slackConnection = null;
	private ObjectMapper jsonMapper = new ObjectMapper();

	//Constructor
	public SlackClientService() {}

	@Override
	public boolean sendMessage(String channel, String text) {
		boolean status = false;

		MessagePayload payload = payloadGen.getStyledMessagePayload(channel, text);
		String payloadJSON = "";
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getPostMsgConnection();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		status = sender.sendMessageAndGetStatus(slackConnection, payloadJSON);

		slackConnection.disconnect();
		slackConnection = null;
		
		return status;
	}

	@Override
	public boolean sendPrivateMessage(String channel, String text, String user) {
		boolean status = false;

		MessagePayload payload = payloadGen.getStyledPrivatePayload(channel, text, user);
		String payloadJSON = "";
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getPrivateMsgConnection();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		status = sender.sendMessageAndGetStatus(slackConnection, payloadJSON);

		slackConnection.disconnect();
		slackConnection = null;
		
		return status;
	}

	@Override
	public String createSchedule(String channel, String text, Date postAt, boolean doRepeat) {
		String messageID = "";
		
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		if(doRepeat) {
			payload = payloadGen.getStyledSchedulePayload(channel, text, postAt);
		}
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getScheduleMsgConnection();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		messageID = sender.sendScheduleAndGetMessageId(slackConnection, payloadJSON);
		
		slackConnection.disconnect();
		slackConnection = null;

		return messageID;
	}

	@Override
	public void deleteSchedule(String channel, String messageID) {
		MessagePayload payload = payloadGen.getStyledScheduleDeletePayload(channel, messageID);
		String payloadJSON = "";
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getDeleteScheduledMsgConnection();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sender.sendMessageAndGetStatus(slackConnection, payloadJSON);
		
		slackConnection.disconnect();
		slackConnection = null;
	}

	@Override
	public String createReminder(String text, String user) {
		String reminderID = "";
		
		Date date = new Date();
		
		MessagePayload payload = payloadGen.getStyledReminderPayload(text, user, date);
		String payloadJSON = "";
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getReminderMsgConnection();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		reminderID = sender.sendReminderAndGetReminderId(slackConnection, payloadJSON);
		
		slackConnection.disconnect();
		slackConnection = null;
		
		return reminderID;
	}

	@Override
	public void deleteReminder(String reminderID) {
		MessagePayload payload = payloadGen.getStyledReminderDeletePayload(reminderID);
		String payloadJSON = "";
		
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getDeleteReminderMsgConnection();
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sender.sendMessageAndGetStatus(slackConnection, payloadJSON);
		
		slackConnection.disconnect();
		slackConnection = null;
	}
	
	@Override
	public HashMap<String,String> getChannelsList() {
		HashMap<String,String> allChannels = new HashMap<String,String>();
		
		try {
			slackConnection = connectionGen.getChannelsListConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		allChannels = sender.sendRequestToGetChannelsList(slackConnection);
		
		return allChannels;
	}

	// Delete on final review --- this method and 2nd implementation --- amer will implement this functionality
	@Override
	public void onApplicationEvent(SlackEventTriggeredEvent event) {
		HashMap<String, String> eventData = event.getEventData();
		sendMessage(eventData.get("channel"), eventData.get("type"));
	}
}
/*This spot is used to park cursor -->> /  \ <<--*/