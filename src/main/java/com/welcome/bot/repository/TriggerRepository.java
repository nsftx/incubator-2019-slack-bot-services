package com.welcome.bot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Trigger;


public interface TriggerRepository extends PagingAndSortingRepository<Trigger, Integer>{
	
	public List<Trigger> findAllByMessage(Message message);
	
	public Page<Trigger> findAll(Pageable pageParam);
	
	public List<Trigger> findAllByTriggerTypeAndChannel(String triggerType, String channel);
	
	public List<Trigger> findAllByChannel(String channel);
}
