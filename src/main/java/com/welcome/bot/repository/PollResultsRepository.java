package com.welcome.bot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.PollResult;

public interface PollResultsRepository extends PagingAndSortingRepository<PollResult, Integer>{
	List<PollResult> findByUserIdAndChoiceIdAndPollId(String userId, Integer choiceId, UUID pollId);
	List<PollResult> findAllByChoiceIdAndPollId(Integer choiceId, UUID pollId);
	List<PollResult> findAllByChoiceId(Integer choiceId);
	

}
