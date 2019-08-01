package com.welcome.bot.slack.api;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.welcome.bot.slack.api.model.messagepayloadmodel.MessagePayload;

@Service
public class SlackCommandService {

	public String handleCommand(String command) {
		String response = "";
		String responseText = "";
		
		switch(command) {
		case "about":
			System.out.println("SlackCommandService SWITCH = ABOUT");
			responseText = "NSoft provides betting solutions to its clients, starting from live betting, bingo, to plenty of different virtual games.";
			response = generateJSONResponse(responseText);
			
			break;
		case "benefits":
			System.out.println("SlackCommandService SWITCH = BENEFITS");
			responseText = "Benefits of working at NSoft are: Fresh fruits every day, flexible working hours, child bonuses, monthly team buildings, "
					+ "monthly cinema visits, and more";
			response = generateJSONResponse(responseText);
			break;
		case "work":
			System.out.println("SlackCommandService SWITCH = WORK");
			responseText = "Flexible work hours enable employees to better organize their time, by allowing them to start working from 07:00h-10:00h and "
					+ "depending on when they start, finish at 15:00h-18:00h.";
			response = generateJSONResponse(responseText);
			break;
		case "docs":
			System.out.println("SlackCommandService SWITCH = DOCS");
			responseText = "Check more information about company and general at link: https://www.nsoft.com";
			response = generateJSONResponse(responseText);
			break;
		case "community":
			System.out.println("SlackCommandService SWITCH = COMMUNITY");
			responseText = "Our company is active at Google+. Here is the link to our community and G+: www.googleplus.com/community/nsoft";
			response = generateJSONResponse(responseText);
			break;
		}
		return response;
	}
	
	private String generateJSONResponse(String responseText) {
		MessagePayload payload = new MessagePayload();
		payload.setText(responseText);
		ObjectMapper jsonMapper = new ObjectMapper();
		String responseJSON = "";
		
		try {
			responseJSON = jsonMapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseJSON;	
	}
}