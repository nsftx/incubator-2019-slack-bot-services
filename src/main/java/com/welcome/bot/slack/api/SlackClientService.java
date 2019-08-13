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
import com.welcome.bot.slack.api.model.interactionpayload.Channel;
import com.welcome.bot.slack.api.model.interactionresponsepayload.InteractionResponsePayload;
import com.welcome.bot.slack.api.model.messagepayload.MessagePayload;
import com.welcome.bot.slack.api.utils.ConnectionGenerator;
import com.welcome.bot.slack.api.utils.MessageSender;
import com.welcome.bot.slack.api.utils.PayloadGenerator;

@Service
public class SlackClientService implements SlackClientApi{

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

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty()) {
			statusMessage.put("status","error");
			statusMessage.put("message","Some or all arguments are either null or empty");
			return statusMessage;
		}
		
		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		payload = payloadGen.getMessagePayload(channel, text);

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

		payload = payloadGen.getPrivateMessagePayload(channel, text, user);

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

		//test
		voteOptions = new ArrayList<>();
		voteOptions.add("Puppet");
		voteOptions.add("Iron Man");
		voteOptions.add("Deadpool");
		voteOptions.add("Puppet (yes, puppet again)");
		text = "Hi there, place your votes :)";
		//test

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || voteOptions == null || voteOptions.isEmpty()) {
			statusMessage.put("status","error");
			statusMessage.put("message","Some or all arguments are either null or empty");
			return statusMessage;
		}

		boolean status = false;
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		payload = payloadGen.getMessagePollPayload(channel, text, voteOptions);

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
	public String createSchedule(String channel, String text, Date postAt) {
		String messageID = "";

		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null) {
			return null;
		}

		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		payload = payloadGen.getSchedulePayload(channel, text, postAt);

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
	public String createSchedule(String channel, String text, Date postAt, String repeatInterval) {
		String messageID = "";
		if(channel == null || channel.isEmpty() || text == null || text.isEmpty() || postAt == null || repeatInterval == null) {
			return null;
		}

		postAt.setDate(postAt.getDate()+1); //TODO test - delete later

		String payloadJSON = "";
		List<MessagePayload> payloads = payloadGen.getScheduleIntervalPayload(channel, text, postAt, repeatInterval);

		System.out.println("PAYLOAD SIZE AT END IS :> " + payloads.size()); //TODO test - delete later

		StringBuilder sb = new StringBuilder();
		for(MessagePayload payload : payloads) {
			try {
				slackConnection = connectionGen.getScheduleMsgConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				payloadJSON = jsonMapper.writeValueAsString(payload);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
			messageID = sender.sendScheduleAndGetMessageId(slackConnection, payloadJSON);
			if(messageID != null) {
				sb.append(messageID);
				sb.append(",");
			}
		}
		if(sb != null) {
			sb = sb.deleteCharAt(sb.length()-1);
			messageID = sb.toString();
		}

		System.out.println("MESSAGE IDs AT END ARE :> " + messageID); //TODO test - delete later

		return messageID;
	}

	@Override
	public boolean deleteSchedule(String messageID, String channel) {
		boolean status = false;

		if(messageID == null || messageID.isEmpty() || channel == null || channel.isEmpty()) {
			return status;
		}
		
		MessagePayload payload;
		String payloadJSON = "";
		
		String[] allMessageIDs = messageID.split(",");
		
		for(String msgID : allMessageIDs) {
			payload = new MessagePayload();
			payload = payloadGen.getScheduleDeletePayload(channel, msgID);
			try {
				payloadJSON = jsonMapper.writeValueAsString(payload);
				slackConnection = connectionGen.getDeleteScheduledMsgConnection();
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			status = sender.sendMessageAndGetStatus(slackConnection, payloadJSON);
		}
		
		slackConnection.disconnect();
		slackConnection = null;

		return status;
	}

	@Override
	public List<Channel> getChannelsList() {
		List<Channel> allChannels = new ArrayList<>();

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

		payload = payloadGen.getMessagePayload(channel, text);
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		String previewPayload = payloadJSON.substring(payloadJSON.indexOf("\"blocks\":")+9, payloadJSON.length()-3);
		String previewLink = "https://api.slack.com/tools/block-kit-builder?blocks=" + previewPayload;

		//System.out.println("Normal Message PREVIEW LINK: " + previewLink);

		return previewLink;
	}

	@Override
	public String getMessagePollPreviewLink(String channel, String text, List<String> voteOptions) {
		MessagePayload payload = new MessagePayload();
		String payloadJSON = "";

		// test
		voteOptions = new ArrayList<>();
		voteOptions.add("Puppet");
		voteOptions.add("Iron Man");
		voteOptions.add("Deadpool");
		voteOptions.add("Puppet (yes, puppet again)");
		text = "Hi there, place your votes :)";
		// test

		payload = payloadGen.getMessagePollPayload(channel, text, voteOptions);
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

	private void deleteAllSchedulesFromSlack() {
		// FOR SCHEDULES - DELETE ALL SCHEDULES
		try {
			slackConnection = connectionGen.getSchedulesListConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<String> allSchedules = sender.sendRequestToGetSchedulesList(slackConnection);

		for(String s : allSchedules) {
			deleteSchedule(s,"#general");
		}

		if (slackConnection != null) {
			slackConnection.disconnect();
			slackConnection = null;
		}
	}
	
	// interaction response
	public void testInteraction(String channel, String text, String responseURL) {
		InteractionResponsePayload payload = new InteractionResponsePayload();
		String payloadJSON = "";

		payload = payloadGen.getInteractionResponsePayload(channel, text);
		try {
			payloadJSON = jsonMapper.writeValueAsString(payload);
			slackConnection = connectionGen.getInteractionResponseConnection(responseURL);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("TEST OF JSON RESPONSE : " + payloadJSON);
		sender.sendMessageAndGetStatus(slackConnection, payloadJSON);
		
	}
	// Delete on final review --- this method and 2nd implementation are Easter Eggs

//	@Override
//	public void onApplicationEvent(SlackEventTriggeredEvent event) {
//		HashMap<String, String> eventData = event.getEventData();
//		
//		String source = eventData.get("isInteraction");
//		
//		if(source.equals("true")) {
//			String text = eventData.get("text");
//			String url = eventData.get("responseURL");
//			testInteraction("#general",text,url);
//		} else if (source.equals("false")) {
//			sendMessagePoll(eventData.get("channel"), eventData.get("type"), new ArrayList<>()).toString();
//		}
		
		
		//TODO test - delete later
//		String response = sendMessage(eventData.get("channel"), eventData.get("type")).toString();
//		String response = sendMessagePoll(eventData.get("channel"), eventData.get("type"), new ArrayList<>()).toString();
//		String response = createSchedule(eventData.get("channel"), eventData.get("type"), new Date(), "wk");
//		System.out.println("RESPONSE FROM SEND MESSAGE (EVENT): " + response);
//		getChannelsList();
	
}
