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
			response = generateJSONResponse("NSoft provides betting solutions to its clients, starting from live betting, bingo, to plenty of different virtual games.");
			break;
		case "benefits":
			response = generateJSONResponse("Benefits of working at NSoft are: Fresh fruits every day, flexible working hours, child bonuses, monthly team buildings, monthly cinema visits, and more");
			break;
		case "work":
			response = generateJSONResponse("Flexible work hours enable employees to better organize their time, by allowing them to start working from 07:00h-10:00h and depending on when they start, finish at 15:00h-18:00h.");
			break;
		case "docs":
			response = generateJSONResponse("Check more information about company and general at link: https://www.nsoft.com");
			break;
		case "community":
			response = generateJSONResponse("Our company is active at Google+. Here is the link to our community and G+: www.googleplus.com/community/nsoft");
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