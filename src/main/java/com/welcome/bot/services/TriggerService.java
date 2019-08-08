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
import com.welcome.bot.exception.MessageNotFoundException;
import com.welcome.bot.exception.TriggerNotFoundException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.models.TriggerCreateDTO;
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
	public TriggerDTO getTrigger(@PathVariable Integer triggerId) {
		
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new TriggerNotFoundException(triggerId));		
		
		TriggerDTO triggerContentDTO = convertToContentDto(trigger);
		
		return triggerContentDTO;
	}
	
	//get all triggers
	public Page<TriggerDTO> getAllTriggers(Pageable pageable){
		
		Page<Trigger> triggerPage = triggerRepository.findAll(pageable);
		
		//preparing data for mapping
		List<Trigger> triggerList = triggerPage.getContent();
		
		//mapping to DTO 
		List<TriggerDTO> triggerContentDTOlist = new ArrayList<>();
		for (Trigger trigger : triggerList) {
			TriggerDTO triggerContentDTO = convertToContentDto(trigger);
			triggerContentDTOlist.add(triggerContentDTO);
		}
		
		//creating page with DTO
		Page<TriggerDTO> triggerContentDTOPage = new PageImpl<TriggerDTO>(triggerContentDTOlist, pageable, triggerPage.getTotalElements());
		
		//return that page
		return triggerContentDTOPage;
	}

	//get trigger by message id
	public List<Trigger> getTriggerByMessage(@PathVariable Integer messageId){
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new MessageNotFoundException(messageId));
		List<Trigger> lista = triggerRepository.findAllByMessage(message);
		return lista;
	}
	
	
	//create trigger
	public TriggerDTO createTrigger(@RequestBody TriggerCreateDTO triggerModel) {
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new MessageNotFoundException(triggerModel.getMessageId()));
			
		Trigger trigger = new Trigger(triggerModel.getChannel(), 
									triggerModel.getTriggerType(),
									triggerModel.isActive(), 
									message);
		
		triggerRepository.save(trigger); 		
		
		TriggerDTO triggerContentDTO = convertToContentDto(trigger);
		return triggerContentDTO;
	}
	
	//update trigger
	public TriggerDTO updateTrigger(@PathVariable Integer triggerId, @RequestBody TriggerCreateDTO triggerModel) {
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new TriggerNotFoundException(triggerId));
		
		//update attributes
		trigger.setActive(triggerModel.isActive());
		trigger.setChannel(triggerModel.getChannel());
		trigger.setTriggerType(triggerModel.getTriggerType());
		trigger.setUpdatedAt();

		//if message is not updated there are no needs to access database
		if(trigger.getMessage().getMessageId() != triggerModel.getMessageId()) {
			Message message = messageRepository.findById(triggerModel.getMessageId())
					.orElseThrow(() -> new MessageNotFoundException(triggerModel.getMessageId()));
			trigger.setMessage(message);
		}
		
		triggerRepository.save(trigger);
	
		TriggerDTO triggerContentDTO = convertToContentDto(trigger);

		return triggerContentDTO;
	}
	
	//delete trigger 
	public ResponseEntity<Trigger> deleteTrigger(@PathVariable Integer triggerId) {
		triggerRepository.deleteById(triggerId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	//delete all triggers by list you send as parameter
	public void deleteAllTriggersByList(List<Trigger> triggerList) {
		if(!triggerList.isEmpty()) {
			triggerRepository.deleteAll(triggerList);	
		}
	}
	
	//converts to trigger dto
	private TriggerDTO convertToContentDto(Trigger trigger) {
		TriggerDTO triggerContentDTO = modelMapper.map(trigger, TriggerDTO.class);
		triggerContentDTO.setMessageDto(modelMapper.map(trigger.getMessage(), MessageDTO.class));
	    return triggerContentDTO;
	}

	//get all trigger by channel
	public List<Trigger> getAllByChannel(String channel) {
		List<Trigger> triggerList = triggerRepository.findAllByChannel(channel);
		return triggerList;
	}

	//update active attribute of list of triggers by boolean parameter you send
	public void updateActiveStatus(List<Trigger> triggerList, boolean active) {
		for (Trigger trigger : triggerList) {
			trigger.setActive(active);
			triggerRepository.save(trigger);
		}
	}

	//deletes all triggers by message you send as parameter
	public void deleteAllTriggersByMessage(Message message) {
		List<Trigger> triggersList = triggerRepository.findAllByMessage(message);
		
		if(!triggersList.isEmpty()) {
			deleteAllTriggersByList(triggersList);
		}	
	}
	
}
