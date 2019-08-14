package com.welcome.bot.slack.api;

import java.io.IOException;
import java.net.URLDecoder;
<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
=======
import java.util.UUID;
>>>>>>> Stashed changes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.bot.slack.api.model.interactionpayload.InteractionPayload;
import com.welcome.bot.slack.api.model.publishevent.PublishInteractionMessage;

@Service
public class SlackInteractionService {
	
	@Autowired
    private ApplicationEventPublisher appEventPublisher;
<<<<<<< Updated upstream
	
	ObjectMapper jsonMapper = new ObjectMapper();
	InteractionPayload payload = new InteractionPayload();
	
	List<String> usersThatVoted = new ArrayList<>();
	
	public String handleInteraction(String interactionRequestPayload) {
		
		String slackPayload = interactionRequestPayload.split("=")[1];

=======
	private ObjectMapper jsonMapper;
	
	@Autowired
	public SlackInteractionService(ObjectMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}
	
	public void handleInteraction(String interactionRequestPayload) {
		String slackPayload = interactionRequestPayload.split("=")[1];
		InteractionPayload payload = new InteractionPayload();
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

		System.out.println("TESTING INTERACTION PAYLOAD RECEIPT | FOR \"RESPONSE URL\", RESULT IS: " + payload.getResponseUrl());
		
		String votedFor = payload.getActions().get(0).getText().getText();
		String voterID = payload.getUser().getId();
		
		
		
		String voted = "Interaction response - You voted for: " + votedFor; // test
		passInteraction("#general", voted, payload.getResponseUrl());
=======
		
		PublishInteractionMessage interactionData = new PublishInteractionMessage();
		interactionData.setChannel(payload.getChannel().getId());
		interactionData.setChoiceSelected(payload.getActions().get(0).getText().getText());
		interactionData.setChoiceID(payload.getActions().get(0).getActionId());
		interactionData.setUser(payload.getUser().getId());
		interactionData.setPollID(UUID.fromString(payload.getActions().get(0).getBlockId()));
>>>>>>> Stashed changes
		
		interactionData.setTimestamp(payload.getContainer().getMessageTs());
		interactionData.setText("Thank You for voting. We will inform all participants on results when poll ends.");
		
		passInteraction(interactionData);
	}
	
<<<<<<< Updated upstream
	private void passInteraction(String channel, String text, String responseURL) {
		HashMap<String, String> interactionData = new HashMap<String,String>();
		interactionData.put("channel", channel);
		interactionData.put("text", text);
		interactionData.put("responseURL", responseURL);
		
		// for tests of interaction
		interactionData.put("isInteraction", "true");
		
		SlackEventTriggeredEvent interactionHandler = new SlackEventTriggeredEvent(this, interactionData);
=======
	private void passInteraction(PublishInteractionMessage interactionData) {
		SlackInteractionTriggeredEvent interactionHandler = new SlackInteractionTriggeredEvent(this, interactionData);
>>>>>>> Stashed changes
		appEventPublisher.publishEvent(interactionHandler);
	}
}