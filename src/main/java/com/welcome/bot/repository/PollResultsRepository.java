package com.welcome.bot.repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.PollResult;

public interface PollResultsRepository extends PagingAndSortingRepository<PollResult, Integer>{
	
	PollResult findByUserIdAndChoiceIdAndPollId(String userId, Integer choiceId, UUID pollId);
	List<PollResult> findAllByChoiceIdAndPollId(Integer choiceId, UUID pollId);
	List<PollResult> findAllByChoiceId(Integer choiceId);
	PollResult findByUserIdAndPollId(String userId, UUID pollId);
	
	//ovo radi
	Integer countByChoiceIdAndPollId(Integer choiceId, UUID pollId);
	
	Map<Integer, Integer> countAllByChoiceIdAndPollId(Integer choiceId, UUID pollId);
	 
	 
//	@Query("SELECT COUNT(pr.pollResultsId), pr.choice_id FROM pollResults pr where pr.choice_id = 3 GROUP BY pr.choice_id")
//	Map<Integer, Integer> findByUserIdAndPollId();
//	
}
