package com.welcome.bot.services;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
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
			//List<PollResult> pollResults = pollResultsRepository.findAllByChoiceIdAndPollId(choice.getChoiceId(), choice.getPoll().getPollUuid());
			Integer result = pollResultsRepository.countByChoiceIdAndPollId(choice.getChoiceId(), choice.getPoll().getPollUuid());
			
			choiceDTO.setCounter(result);
			choiceDTOs.add(choiceDTO);
		}
		return choiceDTOs;
	}	

}
