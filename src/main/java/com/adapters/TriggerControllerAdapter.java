package com.adapters;

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
import org.springframework.web.bind.annotation.RestController;

import com.domain.Trigger;
import com.models.TriggerDTO;
import com.services.TriggerService;

@RestController
public class TriggerControllerAdapter {
	
	@Autowired
	TriggerService triggerService;
	
	//get trigger
	@GetMapping("/api/triggers/{triggerId}")
	public Trigger getTrigger(@PathVariable Integer triggerId) {
		return triggerService.getTrigger(triggerId);
	}
	
	//get all triggers
	@GetMapping("/api/triggers")
	public Page<Trigger> getAllTriggers(Pageable pageable){
		return triggerService.getAllTriggers(pageable);
	}
	
	@GetMapping("/triggersByMessage/{message_id}")
	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		return triggerService.getTriggerByMessage(messageId);
	}
	
	//create trigger
	@PostMapping("/api/triggers")
	public Trigger createTrigger(@RequestBody TriggerDTO triggerModel) {
		return triggerService.createTrigger(triggerModel);		
	}
	
	@PutMapping("/api/triggers/{triggerId}")
	public Trigger updateTrigger(@PathVariable Integer triggerId, @RequestBody TriggerDTO triggerModel) {
		return triggerService.updateTrigger(triggerId, triggerModel);
	}
	
	@DeleteMapping("/api/triggers/{triggerId}")
	public ResponseEntity<Trigger> deleteTrigger(@PathVariable Integer triggerId) {
		return triggerService.deleteTrigger(triggerId);
	}
}
	