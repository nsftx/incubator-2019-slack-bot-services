package com.welcome.bot.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;

public interface ChoiceRepository extends PagingAndSortingRepository<Choice, Integer>{
	List<Choice> findAllByPoll(Poll poll);
}
