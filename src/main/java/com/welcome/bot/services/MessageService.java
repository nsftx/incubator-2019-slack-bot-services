package com.welcome.bot.services;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.Schedule;
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.ScheduleRepository;
import com.welcome.bot.repository.TriggerRepository;

@Service
public class MessageService {
	
	@Autowired 
	MessageRepository messageRepository;
	
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public Page<MessageDTO> getAllMessages(Pageable pageParam){
		
		Page<Message> messagePage = messageRepository.findAll(pageParam);
		
		//preparing data for mapping
		List<Message> messagesList = messagePage.getContent();
		
		//mapping message to DTO
		List<MessageDTO> messageDTOs = modelMapper.map(messagesList, new TypeToken<List<MessageDTO>>(){}.getType());

		//creating Page with DTO
		Page<MessageDTO> messageDTOPage = new PageImpl<MessageDTO>(messageDTOs, pageParam, messagePage.getTotalElements());
		
		return messageDTOPage;
	}
	
	public MessageDTO getMessage(@PathVariable Integer message_id) {
		Message message = messageRepository.findById(message_id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
		return messageDTO;
	}
	
	public @ResponseBody ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageModel, UriComponentsBuilder ucb) {
		Message message = new Message(messageModel.getTitle(), messageModel.getText());
		
		//save message
		message.setCreatedAt();
		messageRepository.save(message);
		
		//make location header
		Integer messageId = message.getMessageId();
		UriComponents uriComponents = ucb.path("/api/message/{id}").buildAndExpand(messageId);
		
		//set atributes to message DTO
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
							
		//return https status with header "location" and response body
		return ResponseEntity
				.created(uriComponents.toUri())
				.body(messageDTO);
	}
	
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		Page<Message> messagesByTitles = messageRepository.findAllByTitle(title, pageParam);
		return messagesByTitles;
	}
	
	public MessageDTO updateMessage(@PathVariable Integer id, @RequestBody MessageDTO messageModel) {
		//find message
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		//set updated attributes of message
		message.setUpdatedAt();
		message.setTitle(messageModel.getTitle());
		message.setText(messageModel.getText());
		
		//save message
		messageRepository.save(message);
		
		//set attributes to message DTO
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class); 
				
		//return message DTO
		return messageDTO;
	}
	
	public ResponseEntity<Message> deleteMessage(@PathVariable Integer id){
		//find message
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		
		//delete all triggers connected with message
		List<Trigger> triggersList = triggerRepository.findAllByMessage(message);
		if(!triggersList.isEmpty()) {
			triggerRepository.deleteAll(triggersList);
		}
		//delete all schedules connected with message
		List<Schedule> schedueList = scheduleRepository.findAllByMessage(message);
		if(!schedueList.isEmpty()) {
			scheduleRepository.deleteAll(schedueList);
		}

		//delete message
		messageRepository.delete(message);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
}



//public ResponseEntity<Page<Message>> getAllMessages2(Pageable pageParam, @PathParam(value = "title") String title){	
//Page<Message> messagePage = null;
//if(title != null) {
//	messagePage = messageRepository.findAllByTitle(title, pageParam);	
//}else {
//	messagePage = messageRepository.findAll(pageParam);	
//}
//
//HttpStatus status = HttpStatus.OK;
//if (messagePage.isEmpty()) {
//	 status = HttpStatus.NO_CONTENT;
//}
//
//return ResponseEntity.status(status).body(messagePage);		
//}
