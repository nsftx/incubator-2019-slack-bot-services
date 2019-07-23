package com.adapters;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domain.Message;
import com.domain.Trigger;
import com.models.TriggerCreateModel;
import com.repository.MessageRepository;
import com.repository.TriggerRepository;

@RestController
public class TriggerControllerAdapter {
	
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	MessageRepository messageRepository;

	//create trigger 
	@PostMapping("/api/trigger")
	public Trigger createTrigger2(@RequestBody TriggerCreateModel triggerModel) {
		ModelMapper modelMapper = new ModelMapper();
		Trigger trigger = new Trigger();
		trigger = modelMapper.map(triggerModel, Trigger.class);
		//messageRepository.save(trigger.getMessage());
		triggerRepository.save(trigger);		
		return trigger;
		
		
		
		
	}
	
	//get trigger
	@GetMapping("/api/trigger/{triggerId}")
	public Trigger getTrigger(@PathVariable Integer triggerId) {
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));		
		return trigger;		
	}
	
	//get all triggers
	@GetMapping("/api/trigger")
	public Page<Trigger> getAllTriggers(Pageable pageable){
		Page<Trigger> list = triggerRepository.findAll(pageable);
		return list;
	}
	
	@GetMapping("/triggerByMessage/{message_id}")
	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));
		List<Trigger> lista = triggerRepository.findAllByMessage(message);
		return lista;
	}

	
//	@PutMapping("/api/trigger")
//	public 
//	
	
	//create trigger version 1 bez model mappera
	
	@PostMapping("/api/trigger-ver1")
	public Trigger createTrigger(@RequestBody TriggerCreateModel triggerModel) {
		Trigger trigger = new Trigger();
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
		
		trigger.setActive(triggerModel.getActive());
		trigger.setChannel(triggerModel.getChannel());
		trigger.setTriggerType(triggerModel.getTriggerType());
		trigger.setMessage(message);
		
		triggerRepository.save(trigger);
		return trigger;
	}
	
	
}

