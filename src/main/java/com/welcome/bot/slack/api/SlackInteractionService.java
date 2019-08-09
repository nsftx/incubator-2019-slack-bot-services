package com.welcome.bot.slack.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.bot.slack.api.model.interactionpayload.InteractionPayload;

@Service
public class SlackInteractionService {
	
	@Autowired
    private ApplicationEventPublisher appEventPublisher;
	
	ObjectMapper jsonMapper = new ObjectMapper();
	InteractionPayload payload = new InteractionPayload();
	
	List<String> usersThatVoted = new ArrayList<>();
	
	public String handleInteraction(String interactionRequestPayload) {
		
		String slackPayload = interactionRequestPayload.split("=")[1];

		try {
			String result = URLDecoder.decode(slackPayload, "UTF-8");
			payload = jsonMapper.readValue(result, InteractionPayload.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("TESTING INTERACTION PAYLOAD RECEIPT | FOR \"RESPONSE URL\", RESULT IS: " + payload.getResponseUrl());
		
		String votedFor = payload.getActions().get(0).getText().getText();
		String voterID = payload.getUser().getId();
		
		
		
		String voted = "Interaction response - You voted for: " + votedFor; // test
		passInteraction("#general", voted, payload.getResponseUrl());
		
		return null;
	}
	
	private void passInteraction(String channel, String text, String responseURL) {
		HashMap<String, String> interactionData = new HashMap<String,String>();
		interactionData.put("channel", channel);
		interactionData.put("text", text);
		interactionData.put("responseURL", responseURL);
		
		// for tests of interaction
		interactionData.put("isInteraction", "true");
		
		SlackEventTriggeredEvent interactionHandler = new SlackEventTriggeredEvent(this, interactionData);
		appEventPublisher.publishEvent(interactionHandler);
	}
}