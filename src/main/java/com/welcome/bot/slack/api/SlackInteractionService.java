package com.welcome.bot.slack.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.UUID;

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

	private ObjectMapper jsonMapper;

	@Autowired
	public SlackInteractionService(ObjectMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public void handleInteraction(String interactionRequestPayload) {
		String slackPayload = interactionRequestPayload.split("=")[1];
		InteractionPayload payload = new InteractionPayload();
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

		PublishInteractionMessage interactionData = new PublishInteractionMessage();
		interactionData.setChannel(payload.getChannel().getId());
		interactionData.setChoiceSelected(payload.getActions().get(0).getText().getText());
		interactionData.setChoiceID(payload.getActions().get(0).getActionId());
		interactionData.setUser(payload.getUser().getId());
		interactionData.setPollID(UUID.fromString(payload.getActions().get(0).getBlockId()));
		interactionData.setText("Thank You for voting. You voted for: " + interactionData.getChoiceSelected());
		passInteraction(interactionData);
	}

	private void passInteraction(PublishInteractionMessage interactionData) {
		SlackInteractionTriggeredEvent interactionHandler = new SlackInteractionTriggeredEvent(this, interactionData);
		appEventPublisher.publishEvent(interactionHandler);
	}
}