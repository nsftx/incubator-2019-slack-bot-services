package com.welcome.bot.services;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;

@Service
public class ChannelService {

	@Autowired
	SlackClientApi slackClientApi;
	
	public String getChannelById(String channelId) {
		List<Channel> list = slackClientApi.getChannelsList();
		System.out.println("trazeni kanal: " + channelId);
		System.out.println(list);
		for (Channel channel : list) {
			System.out.println("kanal: " + channel.getId());
			if(channel.getId().equals(channelId)) {
				return channel.getName();
			}
		}
		//throw new NullPointerException("Channel not found");
		return null;
	}
	
	public String getChannelByName(String channelName) {
		List<Channel> list = slackClientApi.getChannelsList();
		System.out.println(list);
		for (Channel channel : list) {
			if(channel.getName().equals(channelName)) {
				return channel.getId();
			}
		}
		//throw new NullPointerException("Channel not found");
		return null;
	}
}
