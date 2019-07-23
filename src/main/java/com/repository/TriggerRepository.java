package com.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.Trigger;

public interface TriggerRepository extends PagingAndSortingRepository<Trigger, Integer>{
	
}
