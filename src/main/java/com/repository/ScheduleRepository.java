package com.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.Message;
import com.domain.Schedule;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, Integer>{
	
	public List<Schedule> findAllByMessage(Message message);
	
	//public Page<Schedule> findAll(Pageable pageParam);
}
