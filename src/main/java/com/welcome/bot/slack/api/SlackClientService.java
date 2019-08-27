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
import com.welcome.bot.slack.api.utils.ConnectionGenerator;
import com.welcome.bot.slack.api.utils.MessageSender;
import com.welcome.bot.slack.api.utils.PayloadGenerator;

@Service
public class SlackClientService implements SlackClientApi {
	private ConnectionGenerator connectionGen;
	private PayloadGenerator payloadGen;
	private MessageSender sender;
	private ObjectMapper jsonMapper;

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
		payload = payloadGen.getMessagePayload(channel, text);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
		payload = payloadGen.getMessagePayload(channel, text, isSmallImage);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
		payload = payloadGen.getPrivateMessagePayload(channel, text, user);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
		payload = payloadGen.getPrivateMessagePayload(channel, text, user, isSmallImage);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
	public String createPoll(String channel, String text, HashMap<Integer,String> choices, UUID pollID, Date pollCloseTime) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || choices == null || choices.isEmpty() || pollID == null) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		String messageTimestamp = "";
		MessagePayload payload = new MessagePayload();
		payload = payloadGen.getMessagePollPayload(channel, text, choices, pollID.toString());

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
	public void updateMessage(String channel, String text, String messageTimestamp) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || messageTimestamp == null || messageTimestamp.isEmpty()) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		payload = payloadGen.getMessageUpdatePayload(channel, text, messageTimestamp);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
		payload = payloadGen.getMessageDeletePayload(channel, messageTimestamp);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
		payload = payloadGen.getSchedulePayload(channel, text, postAt);

		try {
			String payloadJSON = jsonMapper.writeValueAsString(payload);
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
	public String createRepeatedSchedule(String channel, String text, Date postAt) throws SlackApiException {
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null) {
			throw new InvalidArgumentException("Some/All arguments are null/empty");
		}

		String scheduleID = null;
		List<MessagePayload> payloads = payloadGen.getScheduleIntervalPayload(channel, text, postAt);
		StringBuilder sb = new StringBuilder();
		String id = "";

		for(MessagePayload payload : payloads) {
			try {
				String payloadJSON = jsonMapper.writeValueAsString(payload);
				id = sender.sendScheduleGetScheduleId(connectionGen.getScheduleMsgConnection(), payloadJSON);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
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
		String[] allScheduleIDs = scheduleID.split(",");
		List<Boolean> allStatuses = new ArrayList<>();

		for(String schID : allScheduleIDs) {
			MessagePayload payload = new MessagePayload();
			payload = payloadGen.getScheduleDeletePayload(channel, schID);
			try {
				String payloadJSON = jsonMapper.writeValueAsString(payload);
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
	
	//test
	@EventListener
		public void handleEvent(SlackEventTriggeredEvent event) {
			PublishEventMessage eventData = event.getEventData();
			String channel = eventData.getChannel();
			try {
				sendMessage(channel, "hi");
			} catch (SlackApiException e) {
				e.printStackTrace();
			}
	}
}