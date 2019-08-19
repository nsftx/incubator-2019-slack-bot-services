package com.welcome.bot.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.welcome.bot.domain.Poll;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Integer>{
	Poll findByPollUuid(UUID pollUuid);
}
