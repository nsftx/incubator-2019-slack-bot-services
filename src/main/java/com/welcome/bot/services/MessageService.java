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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.welcome.bot.domain.Message;
import com.welcome.bot.exception.message.MessageNotFoundException;
import com.welcome.bot.exception.message.MessageValidationException;
import com.welcome.bot.models.MessageCreateDTO;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.repository.MessageRepository;

@Service
public class MessageService {
	

	private MessageRepository messageRepository;

	private TriggerService triggerService;

	private ScheduleService scheduleService;

	private ModelMapper modelMapper;
	
	@Autowired
	public MessageService(final MessageRepository messageRepository, 
			final TriggerService triggerService,
			final ScheduleService scheduleService,
			final ModelMapper modelMapper) {
		this.messageRepository = messageRepository;
		this.triggerService = triggerService;
		this.scheduleService = scheduleService;
		this.modelMapper = modelMapper;
	}
	
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
				.orElseThrow(() -> new MessageNotFoundException(message_id));
		
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
		return messageDTO;
	}
	
	public @ResponseBody ResponseEntity<MessageDTO> createMessage(MessageCreateDTO messageModel, UriComponentsBuilder ucb) {
		//throws exception if validation don't pass
		validateMessageInput(messageModel);
		
		Message message = new Message(messageModel.getTitle(), messageModel.getText());
		
		//save message
		messageRepository.save(message);
		
		//make location header
		Integer messageId = message.getMessageId();
		UriComponents uriComponents = ucb.path("/api/message/{id}").buildAndExpand(messageId);
		
		//set attributes to message DTO
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
	
	public MessageDTO updateMessage(Integer id, MessageCreateDTO messageModel) {
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new MessageNotFoundException(id));

		//throws exception if not validated
		validateMessageInput(messageModel);

		//set updated attributes of message
		message.setTitle(messageModel.getTitle());
		message.setText(messageModel.getText());
		
		//save message
		message.setUpdatedAt();
		messageRepository.save(message);

		
		//set attributes to message DTO
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class); 
				
		//return message DTO
		return messageDTO;
	}
	
	public ResponseEntity<MessageDTO> deleteMessage(Integer id){
		//find message
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new MessageNotFoundException(id));
		
		//deletes all triggers connected with message
		triggerService.deleteAllTriggersByMessage(message);
		
		//deletes all scheduals connected with message
		scheduleService.deleteAllSchedulesByMessage(message);

		//delete message
		messageRepository.delete(message);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}	
	
	//validate message input
	private void validateMessageInput(MessageCreateDTO messageModel) throws MessageValidationException{
		if(messageModel.getTitle().length() < 5 || messageModel.getTitle().length() > 30) {
			throw new MessageValidationException(messageModel.getTitle());
		}
		if(messageModel.getText().length() < 20) {
			throw new MessageValidationException(messageModel.getText());
		}
		List<Message> msglist = messageRepository.findAllByTitle(messageModel.getTitle());;
		if(!msglist.isEmpty()) {
			throw new MessageValidationException(messageModel.getTitle(), "Message is duplicate");
		}
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
