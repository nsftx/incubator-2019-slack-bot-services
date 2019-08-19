package com.welcome.bot.services;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.domain.PollResult;
import com.welcome.bot.models.ChoiceDTO;
import com.welcome.bot.repository.ChoiceRepository;
import com.welcome.bot.repository.PollResultsRepository;

@Service
public class ChoiceService {

	@Autowired
	ChoiceRepository choiceRepository;
	
	@Autowired
	PollResultsRepository pollResultsRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public List<Choice> createChoices(Poll poll, List<Choice> choiceList){
		Integer index = 0;
		for (Choice choice : choiceList) {
			choice.setChoiceId(++index);
			choice.setPoll(poll);
			choiceRepository.save(choice);
		}
		return choiceList;
	}

	public List<ChoiceDTO> convertToChoiceDTOs(List<Choice> choiceList) {

		List<ChoiceDTO> choiceDTOs = new ArrayList<>();
		
		for (Choice choice : choiceList) {
				System.out.println(choice);
		}
		
		for (Choice choice : choiceList) {
			ChoiceDTO choiceDTO = modelMapper.map(choice, ChoiceDTO.class);
			System.out.println("choice id" + choice.getChoiceId());
			System.out.println(choice.getPoll().getPollUuid());
			System.out.println(choice.getPoll().getPollUuid().toString());
			List<PollResult> pollResults = pollResultsRepository.findAllByChoiceIdAndPollId(choice.getChoiceId(), choice.getPoll().getPollUuid());
			
			Integer counter = pollResults.size();
			System.out.println("counter" + counter);
			choiceDTO.setCounter(counter);
			choiceDTOs.add(choiceDTO);
		}
		return choiceDTOs;
	}	
}
