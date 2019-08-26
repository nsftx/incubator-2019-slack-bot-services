package com.welcome.bot.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.welcome.bot.domain.Poll;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Integer>{
	Poll findByPollUuid(UUID pollUuid);
	
	List<Poll> findAllByActiveUntilLessThan(Date date);
	
	Page<Poll> findAllByDeleted(Pageable pageable, boolean deleted);
	
}
