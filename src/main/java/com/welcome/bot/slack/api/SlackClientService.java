package com.welcome.bot.slack.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	public HashMap<String,String> sendMessage(String channel, String text) {
		HashMap<String,String> statusMessage = new HashMap<String,String>();
		
		// message preview test
		getMessagePreviewLink(channel, "hi there");
		// poll message preview test
		//getMessagePollPreviewLink(channel, text, new ArrayList<>());

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty()) {
			statusMessage.put("status","error");
			statusMessage.put("message","Some or all arguments are either null or empty");
			return statusMessage;
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		payload = payloadGen.getStyledMessagePayload(channel, text);

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

		if(status) {
			statusMessage.put("status", "ok");
			statusMessage.put("message", "Message sent successfully");
		} else {
			statusMessage.put("status", "error");
			statusMessage.put("message", "Message sending failed");
		}

		return statusMessage;
	}

	@Override
	public HashMap<String,String> sendMessage(String channel, String text, String user) {
		HashMap<String,String> statusMessage = new HashMap<String,String>();

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || user == null || user.isEmpty()) {
			statusMessage.put("status","error");
			statusMessage.put("message","Some or all arguments are either null or empty");
			return statusMessage;
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		payload = payloadGen.getStyledPrivatePayload(channel, text, user);

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

		if(status) {
			statusMessage.put("status", "ok");
			statusMessage.put("message", "Message send successfully");
		} else {
			statusMessage.put("status", "error");
			statusMessage.put("message", "Message sending failed");
		}

		return statusMessage;
	}
	
	@Override
	public HashMap<String, String> sendMessagePoll(String channel, String text, List<String> voteOptions) {
		HashMap<String,String> statusMessage = new HashMap<String,String>();
		
		// commented while testing
		/*if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || voteOptions == null || voteOptions.isEmpty()) {
			statusMessage.put("status","error");
			statusMessage.put("message","Some or all arguments are either null or empty");
			return statusMessage;
		}*/
		
		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		
		//test
		voteOptions = new ArrayList<>();
		voteOptions.add("Puppet");
		voteOptions.add("Iron Man");
		voteOptions.add("Deadpool");
		voteOptions.add("Puppet (yes, puppet again)");
		text = "Hi there, place your votes :)";
		//test
		
		payload = payloadGen.getStyledMessagePollPayload(channel, text, voteOptions);
		
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
		
		if(status) {
			statusMessage.put("status", "ok");
			statusMessage.put("message", "Message sent successfully");
		} else {
			statusMessage.put("status", "error");
			statusMessage.put("message", "Message sending failed");
		}
		
		return statusMessage;
	}

	@Override
	public String createSchedule(String channel, String text, Date postAt, boolean doRepeat) {
		String messageID = "";

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null) {
			return null;
		}

		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		if(doRepeat) {
			payload = payloadGen.getStyledReminderPayload(text, postAt, null);
		} else {
			payload = payloadGen.getStyledSchedulePayload(channel, text, postAt);
		}

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			if(doRepeat) {
				slackConnection = connectionGen.getReminderMsgConnection();
			} else {
				slackConnection = connectionGen.getScheduleMsgConnection();
			}
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(doRepeat) {
			messageID = sender.sendReminderAndGetReminderId(slackConnection, payloadJSON);
		} else {
			messageID = sender.sendScheduleAndGetMessageId(slackConnection, payloadJSON);
		}

		slackConnection.disconnect();
		slackConnection = null;

		return messageID;
	}

	@Override
	public boolean deleteSchedule(String messageID, String channel, boolean wasRepeat) {
		boolean status = false;

		if(messageID == null || messageID.isEmpty() || channel == null || channel.isEmpty()) {
			return status;
		}

		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		if(!wasRepeat) {
			payload = payloadGen.getStyledScheduleDeletePayload(channel, messageID);
		} else {
			payload = payloadGen.getStyledReminderDeletePayload(messageID);
		}

		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			if(!wasRepeat) {
				slackConnection = connectionGen.getDeleteScheduledMsgConnection();
			} else {
				slackConnection = connectionGen.getDeleteReminderMsgConnection();
			}
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
	public List<String> getChannelsList() {
		List<String> allChannels = new ArrayList<>();

		try {
			slackConnection = connectionGen.getChannelsListConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		allChannels = sender.sendRequestToGetChannelsList(slackConnection);

		return allChannels;
	}
	
	@Override
	public String getMessagePreviewLink(String channel, String text) {
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";
		
		payload = payloadGen.getStyledMessagePayload(channel, text);
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		String previewPayload = payloadJSON.substring(payloadJSON.indexOf("\"blocks\":")+9, payloadJSON.length()-3);
		String previewLink = "https://api.slack.com/tools/block-kit-builder?blocks=" + previewPayload;
		
		//System.out.println("Normal Message PREVIEW JSON PAYLOAD: " + payloadJSON);
		//System.out.println("Normal Message PREVIEW LINK: " + previewLink);
		
		return previewLink;
	}

	@Override
	public String getMessagePollPreviewLink(String channel, String text, List<String> voteOptions) {
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		voteOptions = new ArrayList<>();
		voteOptions.add("Puppet");
		voteOptions.add("Iron Man");
		voteOptions.add("Deadpool");
		voteOptions.add("Puppet (yes, puppet again)");
		text = "Hi there, place your votes :)";
		
		payload = payloadGen.getStyledMessagePollPayload(channel, text, voteOptions);
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		String previewPayload = payloadJSON.substring(payloadJSON.indexOf("\"blocks\":")+9, payloadJSON.length()-3);
		String previewLink = "https://api.slack.com/tools/block-kit-builder?blocks=" + previewPayload;
		
		//System.out.println("Message Poll PREVIEW LINK: " + previewLink);
		
		return previewLink;
	}

	// Delete on final review --- this method and 2nd implementation are Easter Eggs
	@Override
	public void onApplicationEvent(SlackEventTriggeredEvent event) {
		HashMap<String, String> eventData = event.getEventData();
//		String response = sendMessage(eventData.get("channel"), eventData.get("type")).toString();
		String response = sendMessagePoll(eventData.get("channel"), eventData.get("type"), new ArrayList<>()).toString();
		System.out.println("RESPONSE FROM SEND MESSAGE (EVENT): " + response);
		
		getChannelsList();
	}
}