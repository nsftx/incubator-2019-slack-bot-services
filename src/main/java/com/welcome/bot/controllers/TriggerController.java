package com.welcome.bot.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.Trigger;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.models.TriggerCreateDTO;
import com.welcome.bot.services.TriggerService;


@RestController
public class TriggerController {
	
	@Autowired
	TriggerService triggerService;
	
	//get trigger
	@GetMapping("/api/triggers/{triggerId}")
	public TriggerDTO getTrigger(@PathVariable Integer triggerId) {
		return triggerService.getTrigger(triggerId);
	}
	
	//get all triggers
	@GetMapping("/api/triggers")
	public Page<TriggerDTO> getAllTriggers(Pageable pageable, @CurrentUser UserPrincipal userPrincipal){
		return triggerService.getAllTriggers(pageable, userPrincipal);
	}
	
	//get trigger by Message
	@GetMapping("/triggersByMessage/{message_id}")
	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		return triggerService.getTriggerByMessage(messageId);
	}
	
	//create trigger
	@PostMapping("/api/triggers")
	public TriggerDTO createTrigger(@RequestBody TriggerCreateDTO triggerModel, @CurrentUser UserPrincipal userPrincipal) {
		return triggerService.createTrigger(triggerModel, userPrincipal);		
	}
	
	@PutMapping("/api/triggers/{triggerId}")
	public TriggerDTO updateTrigger(@PathVariable Integer triggerId, @RequestParam(name = "active") boolean active) {
		return triggerService.updateTrigger(triggerId, active);
	}
	
	@DeleteMapping("/api/triggers/{triggerId}")
	public ResponseEntity<Trigger> deleteTrigger(@PathVariable Integer triggerId) {
		return triggerService.deleteTrigger(triggerId);
	}
}
	