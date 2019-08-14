package com.welcome.bot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Poll;
import com.welcome.bot.models.PollCreateDTO;
import com.welcome.bot.models.PollDTO;
import com.welcome.bot.services.PollService;

@RestController
public class PollController {

	@Autowired
	PollService pollService;
	
	@PostMapping("/api/polls")
	public @ResponseBody PollDTO createMessage(@RequestBody PollCreateDTO pollModel) {
		return pollService.createPoll(pollModel);
	}
	
	@GetMapping("/api/polls")
	public @ResponseBody Iterable<Poll> getAllMessages() {
		return pollService.getAllMessages();
	}
	
}
