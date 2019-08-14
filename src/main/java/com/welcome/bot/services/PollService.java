package com.welcome.bot.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Choice;
import com.welcome.bot.domain.Poll;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.models.ChoiceDTO;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.PollCreateDTO;
import com.welcome.bot.models.PollDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.repository.ChoiceRepository;
import com.welcome.bot.repository.PollRepository;

@Service
public class PollService {
	@Autowired
	PollRepository pollRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	ChoiceService choiceService;
	
	@Autowired
	ChoiceRepository choiceRepository;
	
	public PollDTO createPoll(PollCreateDTO pollModel) {
		Poll poll = new Poll(pollModel.getTitle(), pollModel.getChannel());
		pollRepository.save(poll);
		
		List<Choice> choiceList = modelMapper.map(pollModel.getAnswerList(), new TypeToken<List<Choice>>(){}.getType());
		
		choiceService.saveChoices(choiceList, poll);
		
		PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
		pollDTO.setAnswerList(modelMapper.map(choiceList, new TypeToken<List<ChoiceDTO>>(){}.getType()));
		
		return pollDTO;
	}

	public Page<PollDTO> getAllPolls(Pageable pageable) {
		Page<Poll> pollPage = pollRepository.findAll(pageable);
		
		List<Poll> pollList = pollPage.getContent();
		
		List<PollDTO> pollDtoList = new ArrayList<>();
		for (Poll poll : pollList) {
			List<Choice> choiceList = choiceRepository.findAllByPoll(poll);
			PollDTO pollDTO = convertToContentDto(poll, choiceList);
			pollDtoList.add(pollDTO);
		}
		
		Page<PollDTO> pollDtoPage = new PageImpl<PollDTO>(pollDtoList, pageable, pollPage.getTotalElements());
		
		return pollDtoPage;
	}

	private PollDTO convertToContentDto(Poll poll, List<Choice> choiceList) {
		PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
		pollDTO.setAnswerList(modelMapper.map(choiceList, new TypeToken<List<ChoiceDTO>>(){}.getType()));
		return pollDTO;
	}
	
	
}
