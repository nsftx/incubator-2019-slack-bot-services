package com.welcome.bot.services;


import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.exception.message.MessageNotFoundException;
import com.welcome.bot.exception.trigger.TriggerNotFoundException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.models.UserDTO;
import com.welcome.bot.models.TriggerCreateDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.TriggerRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.UserPrincipal;

@Service
public class TriggerService {

	TriggerRepository triggerRepository;
	
	MessageRepository messageRepository;
	
	ModelMapper modelMapper;
	
	UserRepository userRepository;
	
	MessageService messageService;
	
	ChannelService channelService;
	
	@Autowired
	public TriggerService(final TriggerRepository triggerRepository,
			final MessageRepository messageRepository,
			final ModelMapper modelMapper,
			final UserRepository userRepository,
			final MessageService messageService,
			final ChannelService channelService) {
		this.triggerRepository = triggerRepository;
		this.messageRepository = messageRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.messageService = messageService;
		this.channelService = channelService;
	}

	//get trigger
	public TriggerDTO getTrigger(Integer triggerId) {
		
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new TriggerNotFoundException(triggerId));		
		
		TriggerDTO triggerContentDTO = convertToDto(trigger);
		
		return triggerContentDTO;
	}
	
	//get all triggers
	public Page<TriggerDTO> getAllTriggers(Pageable pageable, UserPrincipal userPrincipal){
		//final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		
		Page<Trigger> triggerPage = null;

		if(user.getRole().equals("ADMIN")) {
			triggerPage = triggerRepository.findAllByDeleted(pageable, false);
		}
		
		else if(user.getRole().equals("USER")) {
			triggerPage = triggerRepository.findAllByUserAndDeleted(pageable, user, false);
		}	
		
		//preparing data for mapping
		List<Trigger> triggerList = triggerPage.getContent();
		
		//mapping to DTO 
		List<TriggerDTO> triggerContentDTOlist = convertToListDto(triggerList);
		
		//creating page with DTO
		Page<TriggerDTO> triggerContentDTOPage = new PageImpl<TriggerDTO>(triggerContentDTOlist, pageable, triggerPage.getTotalElements());
		
		//return that page
		return triggerContentDTOPage;
	}

	//get trigger by message id
	public List<Trigger> getTriggerByMessage(Integer messageId){
		Message message = messageRepository.findById(messageId)
				.orElseThrow(() -> new MessageNotFoundException(messageId));
		List<Trigger> lista = triggerRepository.findAllByMessage(message);
		return lista;
	}
	
	
	//create trigger
	public TriggerDTO createTrigger(TriggerCreateDTO triggerModel, UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new MessageNotFoundException(triggerModel.getMessageId()));
		
		
		//String channelName = channelService.getChannelById(triggerModel.getChannelId());
		String tarikovKanal = "tarikov kanaala";
		
		
		Trigger trigger = new Trigger(tarikovKanal,
									triggerModel.getChannelId(), 
									triggerModel.getTriggerType(),
									triggerModel.isActive(), 
									message,
									user);
		
		triggerRepository.save(trigger); 		
		
		TriggerDTO triggerDTO = convertToDto(trigger);
		return triggerDTO;
	}
	
	//update trigger
	public TriggerDTO updateTrigger(Integer triggerId, boolean active) {
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new TriggerNotFoundException(triggerId));
	
		if(trigger.isActive() != active) {
			trigger.setActive(active);
			trigger.setUpdatedAt();
			triggerRepository.save(trigger);
		}
		TriggerDTO triggerContentDTO = convertToDto(trigger);

		return triggerContentDTO;
	}
	
	//delete trigger 
	public ResponseEntity<Trigger> deleteTrigger(Integer triggerId) {
		Trigger trigger = triggerRepository.findById(triggerId).orElseThrow();
		
		softDelete(trigger);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	private void softDelete(Trigger trigger) {
		trigger.setDeleted(true);
		trigger.setActive(false);
		triggerRepository.save(trigger);
	}
	
	//delete all triggers by list you send as parameter
	public void deleteAllTriggersByList(List<Trigger> triggerList) {
		for (Trigger trigger : triggerList) {
			trigger.setDeleted(true);
		}
	}
	
	//get all trigger by channel
	public List<Trigger> getAllByChannelId(String channelId) {
		List<Trigger> triggerList = triggerRepository.findAllByChannelId(channelId);
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
	
	
	//CONVERTS to trigger dto
	private TriggerDTO convertToDto(Trigger trigger) {
		TriggerDTO triggerContentDTO = modelMapper.map(trigger, TriggerDTO.class);
		triggerContentDTO.setMessageDto(messageService.convertToDto(trigger.getMessage()));
		triggerContentDTO.setUserDto(modelMapper.map(trigger.getUser(), UserDTO.class));
	    return triggerContentDTO;
	}
	
	private List<TriggerDTO> convertToListDto(List<Trigger> triggerList){
		List<TriggerDTO> triggerListDtos = new ArrayList<>();
		for (Trigger trigger : triggerList) {
			TriggerDTO triggerContentDTO = convertToDto(trigger);
			triggerListDtos.add(triggerContentDTO);
		}	
		return triggerListDtos;
	}
	
}
