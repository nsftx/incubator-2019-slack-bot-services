package com.welcome.bot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.repository.ChoiceRepository;

@Service
public class ChoiceService {

	@Autowired
	ChoiceRepository choiceRepository;
	
	public List<Choice> saveChoices(List<Choice> choiceList, Poll poll){
		for (Choice choice : choiceList) {
			choice.setPoll(poll);
			choiceRepository.save(choice);
		}
		return choiceList;
	}
	
}
