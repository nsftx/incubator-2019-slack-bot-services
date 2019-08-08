package com.welcome.bot.slack.api;

import org.springframework.stereotype.Service;

import com.welcome.bot.slack.api.model.interactionpayload.InteractionPayload;

@Service
public class SlackInteractionService {
	
	public String handleInteraction(InteractionPayload interactionPayload) {
		
		// test receipt of data
		String interData = interactionPayload.getResponseUrl();
		System.out.println("TESTING INTERACTION PAYLOAD RECEIPT | FOR \"RESPONSE URL\", RESULT IS: " + interData);
		
		return null;
	}

}
