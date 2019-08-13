package com.welcome.bot.controllers;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.services.SlackApiEventListener;

@RestController
public class ChannelsController {
	
	@Autowired
	SlackApiEventListener slackService;
	
	//get list of channels when creating schedule
	@GetMapping("/api/channels")
	public Map<String, String> getChannelsList() {
		
	    HashMap<String, String> map = new HashMap<>();
	    map.put("ID1234", "general");
	    map.put("ID3123", "random");
	    map.put("ID331231", "samotarikovakanal");
	    return map;
		//return slackService.getChannelsList();
	}
}
