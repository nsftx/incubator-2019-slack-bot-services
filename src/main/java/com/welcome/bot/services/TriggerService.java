package com.welcome.bot.services;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.TriggerContentDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.TriggerRepository;

@Service
public class TriggerService {

	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	//get trigger
	public TriggerContentDTO getTrigger(@PathVariable Integer triggerId) {
		
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger data not found"));		
		
		TriggerContentDTO triggerContentDTO = convertToContentDto(trigger);
		
		return triggerContentDTO;
	}
	
	//get all triggers
	public Page<TriggerContentDTO> getAllTriggers(Pageable pageable){
		
		Page<Trigger> triggerPage = triggerRepository.findAll(pageable);
		
		//preparing data for mapping
		List<Trigger> triggerList = triggerPage.getContent();
		
		//mapping to DTO 
		List<TriggerContentDTO> triggerContentDTOlist = new ArrayList<>();
		for(Integer i = 0; i < triggerPage.getTotalElements(); i++) {
			Trigger trigger = triggerList.get(i);
			TriggerContentDTO triggerContentDTO = convertToContentDto(trigger);
			triggerContentDTOlist.add(triggerContentDTO);
		}
		
		//creating page with DTO
		Page<TriggerContentDTO> triggerContentDTOPage = new PageImpl<TriggerContentDTO>(triggerContentDTOlist, pageable, triggerPage.getTotalElements());
		
		//return that page
		return triggerContentDTOPage;

	}

	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));
		List<Trigger> lista = triggerRepository.findAllByMessage(message);
		return lista;
	}
	
	
	//create trigger
	public TriggerContentDTO createTrigger(@RequestBody TriggerDTO triggerModel) {
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
		
		Trigger trigger = new Trigger(triggerModel.getChannel(), 
									triggerModel.getTriggerType(),
									triggerModel.isActive(), 
									message);
		
		triggerRepository.save(trigger); 		
		
		TriggerContentDTO triggerContentDTO = convertToContentDto(trigger);
		return triggerContentDTO;
	}
	
	public TriggerContentDTO updateTrigger(@PathVariable Integer triggerId, @RequestBody TriggerDTO triggerModel) {
		Trigger trigger = triggerRepository.findById(triggerId).orElseThrow();
		trigger.setActive(triggerModel.isActive());
		trigger.setChannel(triggerModel.getChannel());
		trigger.setTriggerType(triggerModel.getTriggerType());		

		//if message is not updated there are no needs to access database
		if(trigger.getMessage().getMessageId() != triggerModel.getMessageId()) {
			Message message = messageRepository.findById(triggerModel.getMessageId()).orElseThrow();
			trigger.setMessage(message);
		}
		
		//add trigger content to DTO class
		// add message content to message DTO
		TriggerContentDTO triggerContentDTO = convertToContentDto(trigger);

		//return trigger content dto
		return triggerContentDTO;
	}
	
	public ResponseEntity<Trigger> deleteTrigger(@PathVariable Integer triggerId) {
		triggerRepository.deleteById(triggerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	private TriggerContentDTO convertToContentDto(Trigger trigger) {
		TriggerContentDTO triggerContentDTO = modelMapper.map(trigger, TriggerContentDTO.class);
		triggerContentDTO.setMessageDto(modelMapper.map(trigger.getMessage(), MessageDTO.class));
	    return triggerContentDTO;
	}
	
}
