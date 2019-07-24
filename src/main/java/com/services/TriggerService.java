package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.domain.Message;
import com.domain.Trigger;
import com.models.TriggerDTO;
import com.repository.MessageRepository;
import com.repository.TriggerRepository;

@Service
public class TriggerService {
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	//get trigger
	public Trigger getTrigger(@PathVariable Integer triggerId) {
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));		
		return trigger;		
	}
	
	//get all triggers
	public Page<Trigger> getAllTriggers(Pageable pageable){
		Page<Trigger> list = triggerRepository.findAll(pageable);
		return list;
	}

	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));
		List<Trigger> lista = triggerRepository.findAllByMessage(message);
		return lista;
	}
	
	
	//create trigger
	public Trigger createTrigger(@RequestBody TriggerDTO triggerModel) {
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
		Trigger trigger = new Trigger(triggerModel.getChannel(), triggerModel.getTriggerType(), triggerModel.getActive(), message);	
		return triggerRepository.save(trigger); 		
	}
	
	public Trigger updateTrigger(@PathVariable Integer triggerId, @RequestBody TriggerDTO triggerModel) {
		Trigger trigger = triggerRepository.findById(triggerId).orElseThrow();
		trigger.setActive(triggerModel.getActive());
		trigger.setChannel(triggerModel.getChannel());
		trigger.setTriggerType(triggerModel.getTriggerType());
		if(trigger.getMessage().getId() != triggerModel.getMessageId()) {
			Message message = messageRepository.findById(triggerModel.getMessageId()).orElseThrow();
			trigger.setMessage(message);
		}
		return trigger;
	}
	
	public ResponseEntity<Trigger> deleteTrigger(@PathVariable Integer triggerId) {
		triggerRepository.deleteById(triggerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}
