package com.welcome.bot.services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.query.criteria.internal.expression.function.SubstringFunction;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.FinalizablePhantomReference;
import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.domain.PollResult;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.exception.base.BaseException;
import com.welcome.bot.repository.PollRepository;
import com.welcome.bot.repository.PollResultsRepository;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.slack.api.EventType;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;
import com.welcome.bot.slack.api.model.publishevent.PublishEventMessage;

import net.bytebuddy.description.ModifierReviewable.OfAbstraction;
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
	
	PollResultsRepository pollResultsRepository;
	
	@Autowired
	public SlackService(final SlackClientApi slackClientApi,
			final MessageService messageService, 
			final ScheduleService scheduleService,
			final TriggerService triggerService,
			final TriggerRepository triggerRepository, 
			final AuditService auditService,
			final ChannelService channelService,
			final PollResultsRepository pollResultsRepository) {
		this.slackClientApi = slackClientApi;
		this.messageService = messageService;
		this.scheduleService = scheduleService;
		this.triggerService = triggerService;
		this.triggerRepository = triggerRepository;
		this.auditService = auditService;
		this.channelService = channelService;
		this.pollResultsRepository = pollResultsRepository;
	}

	public void triggerApp(String eventType, String channelId) {
		List<Trigger> list = triggerRepository.findAllByTriggerTypeAndChannelId(eventType, channelId);
		for (Trigger trigger : list) {
			try {
				slackClientApi.sendMessage(trigger.getChannelId(), trigger.getMessage().getText());
			} catch (SlackApiException e) {
				// dont forget to throw your exception
				e.printStackTrace();
			}
		}
	}
	
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void logChannelActivities(String channelId) {
		
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
	
	//get all channels - sluzi samo za klijenta, da bi dobio kanale bez "#"
	public JSONArray getChannelsList(){
		List<Channel> channelList = new ArrayList<>();
		
		JSONArray channelArray = new JSONArray();
		
		
		//channelList = slackClientApi.getChannelsList();
		try {
			channelList = slackClientApi.getChannelsList();
		}catch (JSONException e) {
			throw new BaseException("Problems with Slack Conectivity");
		}
		
		for (Channel channel : channelList) {
			JSONObject channelJson = new JSONObject();
			channelJson.put("id", channel.getId());
			channelJson.put("channelName", channel.getName().substring(1));
			channelArray.add(channelJson);
		}
		
		return channelArray;
	}	
	
	
	public JSONArray getMockupChannels() {
			JSONArray channelList = new JSONArray();

			JSONObject daily = new JSONObject();
			daily.put("id", "1");
			daily.put("type", "general mockup");
			
			JSONObject weekly = new JSONObject();
			weekly.put("id", "2");
			weekly.put("type", "random mockup");
			
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
	
	public JSONArray getAllTriggers() {
//		JSONArray triggerArray = new JSONArray();
//		
//		List<Enum> enumValues = Arrays.asList(EventType.values());
//
//		JSONObject triggerJson = new JSONObject();
//		triggerJson.put("triggers", enumValues);
		
		JSONArray triggerArray = new JSONArray();

		JSONObject obj = new JSONObject();
		obj.put("type", "app_mention");
		
		triggerArray.add(obj);
		
		return triggerArray;
	}

	public String createPoll(Poll poll, HashMap<Integer, String> choicesMap, String channelId, Date activeUntil) {

		try {
			String slackTimestamp = slackClientApi.createPoll(channelId, poll.getTitle(), choicesMap, poll.getPollUuid(), activeUntil);
			return slackTimestamp;
		}catch (Exception e) {
			throw new BaseException("Couldn't send poll to slack");
		}
	}

	public void createAVote(String userId, Integer choiceId, UUID pollId) {
		PollResult pollResult = pollResultsRepository.findByUserIdAndPollId(userId, pollId);
		System.out.println(pollResult);
		if(pollResult == null) {
			pollResult = new PollResult(userId, choiceId, pollId);
		}else {
			pollResult.setChoiceId(choiceId);
		}
		
		pollResultsRepository.save(pollResult);
		
	}
}	

