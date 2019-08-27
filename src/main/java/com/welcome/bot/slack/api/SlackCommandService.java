package com.welcome.bot.slack.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.bot.slack.api.model.messagepayload.MessagePayload;

@Service
public class SlackCommandService {

	private ObjectMapper jsonMapper;

	@Autowired
	public SlackCommandService(ObjectMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
	}

	public String handleCommand(String command) {
		String response = "";

		switch(command) {
		case "about":
			response = generateJSONResponse("NSoft's mission is to provide bet shop owners with a powerful omni-channel platform, visually appealing and revenue generating virtual games and data-packed sportsbook, in order to help them grow their business.");
			break;
		case "benefits":
			response = generateJSONResponse("NSoft offers plenty of additional benefits to their employees. Some of these are:\n-Free stuff\n-Monthly cinema visits\n-Subsidized food and drinks\n-Flexible work hours\n-Events,games,etc\n-Rewards\n\n For more info check <http://www.nsoft.com/careers/|Nsoft Careers - Perks");
			break;
		case "work":
			response = generateJSONResponse("Flexible working hours allow employees to work 8h/day in range starting from 7:00AM-10:00AM and working until 3:00PM-6:00PM.");
			break;
		case "docs":
			response = generateJSONResponse("More information on NSoft products on <http://www.nsoft.com/betting-software|NSoft Products>");
			break;
		case "community":
			response = generateJSONResponse("Our company is active on social media platforms. Check out our Instagram profile: <http://www.instagram.com/nsoftcompany|NSoft-Instagram>");
			break;
		}
		return response;
	}

	private String generateJSONResponse(String responseText) {
		MessagePayload payload = new MessagePayload();
		payload.setText(responseText);
		String responseJSON = "";

		try {
			responseJSON = jsonMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseJSON;	
	}
}