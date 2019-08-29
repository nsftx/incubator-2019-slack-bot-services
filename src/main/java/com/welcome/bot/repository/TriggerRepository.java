package com.welcome.bot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.domain.User;


public interface TriggerRepository extends PagingAndSortingRepository<Trigger, Integer>{
	
	public List<Trigger> findAllByMessage(Message message);
	
	public Page<Trigger> findAll(Pageable pageParam);
	
	public Page<Trigger> findAllByUser(Pageable pageParam, User user);
	
	public List<Trigger> findAllByTriggerTypeAndChannel(String triggerType, String channel);
	
	public List<Trigger> findAllByTriggerTypeAndChannelId(String triggerType, String channelId);
	
	public List<Trigger> findAllByTriggerTypeAndChannelIdAndActive(String triggerType, String channelId, boolean active);
	
	public List<Trigger> findAllByTriggerTypeAndChannelAndActive(String triggerType, String channel, boolean active);
	
	public Page<Trigger> findAllByDeleted(Pageable pageParam, boolean deleted);
	
	public List<Trigger> findAllByChannel(String channel);
	
	public List<Trigger> findAllByChannelId(String channelId);
	
	public Page<Trigger> findAllByUserAndDeleted(Pageable pageParam, User user, boolean deleted);
	
	
}
