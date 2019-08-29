package com.welcome.bot.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.services.SlackApiEventListener;
import com.welcome.bot.services.SlackService;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@RestController
public class ChannelsController {
	
	@Autowired
	SlackService slackService;
	
	//MOCKUP CHANNELS
	@GetMapping("/api/channels")
	public JSONArray getChannelsList() {
		return slackService.getChannelsList();
	}
	
	@GetMapping("/api/slack-triggers")
	public JSONArray getTriggerList() {
		return slackService.getAllSlackTriggers();
	}
	
	
}
