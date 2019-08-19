package com.welcome.bot.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.FinalizablePhantomReference;
import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;
import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Service
public class SlackService {

	SlackClientApi slackClientApi; 
	 
	MessageService messageService;
	
	ScheduleService scheduleService;

	TriggerService triggerService;
	
	TriggerRepository triggerRepository;
	
	AuditService auditService;
	
	ChannelService channelService;
	
	@Autowired
	public SlackService(final SlackClientApi slackClientApi,
			final MessageService messageService, 
			final ScheduleService scheduleService,
			final TriggerService triggerService,
			final TriggerRepository triggerRepository, 
			final AuditService auditService,
			final ChannelService channelService) {
		this.slackClientApi = slackClientApi;
		this.messageService = messageService;
		this.scheduleService = scheduleService;
		this.triggerService = triggerService;
		this.triggerRepository = triggerRepository;
		this.auditService = auditService;
		this.channelService = channelService;
	}

	public void triggerApp(String eventType, String channel) {
		
		List<Trigger> list = triggerRepository.findAllByTriggerTypeAndChannel(eventType, channel);
		for (Trigger trigger : list) {
			try {
				slackClientApi.sendMessage(trigger.getChannel(), trigger.getMessage().getText());
			} catch (SlackApiException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void logChannelActivities(PublishEventMessage eventData) {
		String channelId = eventData.getChannel();
		
		List<Schedule> scheduleList = scheduleService.getAllByChannelId(channelId);
		List<Trigger> triggerList = triggerService.getAllByChannelId(channelId);
		
		if(!scheduleList.isEmpty()) {
			auditService.createScheduleLog(scheduleList);
			scheduleService.deleteAllSchedulesByList(scheduleList);
		}
		if(!triggerList.isEmpty()) {
			auditService.createTriggerLog(triggerList);
			triggerService.deleteAllTriggersByList(triggerList);
		}
	}
	
	public List<Channel> getChannelsList() {
		return slackClientApi.getChannelsList();
	}	
	public JSONArray getMockupChannels() {
			JSONArray channelList = new JSONArray();

			JSONObject daily = new JSONObject();
			daily.put("id", "1");
			daily.put("type", "kanal 1");
			
			JSONObject weekly = new JSONObject();
			weekly.put("id", "2");
			weekly.put("type", "kanal 2");
			
			JSONObject monthly = new JSONObject();
			monthly.put("id", "3");
			monthly.put("type", "kanal 3");
			
			channelList.add(daily);
			channelList.add(weekly);
			channelList.add(monthly);

			for (Object object : channelList) {
				JSONObject jsonObject = (JSONObject) object;
				System.out.println(jsonObject.get("type"));
			}
			return channelList;
		}

	public void createPoll(Poll poll, List<Choice> choiceList, String channelId) {
		//preparing and sending to slack
		HashMap<Integer, String> choicesValuesToNumbersMap = new HashMap<>();
		for (Choice choice : choiceList) {
			choicesValuesToNumbersMap.put(choice.getChoiceId(), choice.getChoiceValue());
		}
		slackClientApi.sendMessagePoll(channelId, poll.getTitle(), choicesValuesToNumbersMap, poll.getPollUuid());
	}
}	

