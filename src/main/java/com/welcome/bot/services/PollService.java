package com.welcome.bot.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Poll;
import com.welcome.bot.models.PollCreateDTO;
import com.welcome.bot.models.PollDTO;
import com.welcome.bot.repository.PollRepository;

@Service
public class PollService {
	@Autowired
	PollRepository pollRepository;

	@Autowired
	ModelMapper modelMapper;

	public PollDTO createPoll(PollCreateDTO pollModel) {

		Poll poll = new Poll(pollModel.getTitle(), pollModel.getText(), pollModel.getChannel());
		pollRepository.save(poll);
		PollDTO pollDTO = modelMapper.map(poll, PollDTO.class);
		return pollDTO;
	}

	public Iterable<Poll> getAllMessages() {
		Iterable<Poll> list = pollRepository.findAll();
		return list;
	}
}
