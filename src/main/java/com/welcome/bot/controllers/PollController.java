package com.welcome.bot.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Poll;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.PollCreateDTO;
import com.welcome.bot.models.PollDTO;
import com.welcome.bot.services.PollService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@RestController
public class PollController {

	@Autowired
	PollService pollService;
	
	@PostMapping("/api/polls")
	public @ResponseBody PollDTO createMessage(@RequestBody PollCreateDTO pollModel) {
		return pollService.createPoll(pollModel);
	}
	
	@GetMapping("/api/polls")
	public @ResponseBody Page<PollDTO> getAllPolls(Pageable pageable) {
		return pollService.getAllPolls(pageable);
	}
	@GetMapping("/api/polls/{pollId}")
	public @ResponseBody PollDTO getPoll(Pageable pageable, @PathVariable Integer pollId) {
		return pollService.getPoll(pageable, pollId);
	}
	
	@DeleteMapping("/api/polls/{pollId}")
	public @ResponseBody ResponseEntity<Object> deletePoll(Pageable pageable, @PathVariable Integer pollId) {
		return pollService.deletePoll(pageable, pollId);
	}
	
}
