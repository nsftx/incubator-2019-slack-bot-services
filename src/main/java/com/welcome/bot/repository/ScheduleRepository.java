package com.welcome.bot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.User;



public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer>{
	
	public List<Schedule> findAllByMessage(Message message);
	
	public List<Schedule> findAllByChannel(String channel);
	
	public List<Schedule> findAllByChannelId(String channelId);
	
	public Page<Schedule> findAllByUser(Pageable pageable, User user);
	
	public Page<Schedule> findAllByUserAndDeleted(Pageable pageable, User user, boolean deleted);
	
	public Page<Schedule> findAllByDeleted(Pageable pageable, boolean deleted);
	
}
