package com.welcome.bot.services;


import java.util.ArrayList;
import java.util.Date;
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
import com.welcome.bot.domain.Trigger;
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.exception.message.MessageNotFoundException;
import com.welcome.bot.exception.message.MessageValidationException;
import com.welcome.bot.models.MessageCreateDTO;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.TriggerDTO;
import com.welcome.bot.models.UserDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.UserPrincipal;

@Service
public class MessageService {	
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private TriggerService triggerService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private UserRepository userRepository;
	
	public Page<MessageDTO> getAllMessages(Pageable pageParam, UserPrincipal userPrincipal){
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		
		Page<Message> messagePage = null;
		
		if(user.getRole().equals("ADMIN")) {
			messagePage = messageRepository.findAllByDeleted(pageParam, false);
		}
		else if(user.getRole().equals("USER")) {
			messagePage = messageRepository.findAllByUserAndDeleted(pageParam, user, false);
		}
		
		//preparing data for mapping
		List<Message> messagesList = messagePage.getContent();

		//mapping message to DTO
		List<MessageDTO> messageDTOs = convertToListDtos(messagesList);

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
	
	public @ResponseBody MessageDTO createMessage(MessageCreateDTO messageModel, UserPrincipal userPrincipal) {
		//throws exception if validation don't pass
		validateMessageInput(messageModel);
		
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		
		Message message = new Message(messageModel.getTitle(), messageModel.getText(), user);
		
		//save message
		messageRepository.save(message);
				
		//set attributes to message DTO
		MessageDTO messageDTO = convertToDto(message);
							
		//return https status with header "location" and response body
		return messageDTO;
	}
	
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		Page<Message> messagesByTitles = messageRepository.findAllByTitle(title, pageParam);
		return messagesByTitles;
	}
	
	public MessageDTO updateMessage(Integer id, MessageCreateDTO messageModel) {
		Message message = messageRepository.findById(id)
				.orElseThrow(() -> new MessageNotFoundException(id));

		//throws exception if not validated
		if(!messageModel.getTitle().equals(message.getTitle())) {
			validateMessageDuplicates(messageModel);
		}
		//throws exceptioon
		validateMessageInput(messageModel);

		//set updated attributes of message
		message.setTitle(messageModel.getTitle());
		message.setText(messageModel.getText());
		
		//save message
		message.setUpdatedAt();
		messageRepository.save(message);
		
		//set attributes to message DTO
		MessageDTO messageDTO = convertToDto(message);
				
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
		softDelete(message);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}	
	
	private void softDelete(Message message) {
		message.setDeleted(true);
		messageRepository.save(message);
	}
	
	//validate message input
	private void validateMessageInput(MessageCreateDTO messageModel) throws MessageValidationException{
		if(messageModel.getTitle().length() < 5 || messageModel.getTitle().length() > 30) {
			throw new MessageValidationException(messageModel.getTitle());
		}
		if(messageModel.getText().length() < 20) {
			throw new MessageValidationException(messageModel.getText());
		}
	}
	
	private void validateMessageDuplicates(MessageCreateDTO messageModel) throws MessageValidationException {
		List<Message> msglist = messageRepository.findAllByTitle(messageModel.getTitle());;
		if(!msglist.isEmpty()) {
			throw new MessageValidationException(messageModel.getTitle(), "Message is duplicate");
		}		
	}

	
	public MessageDTO convertToDto(Message message) {
		MessageDTO messageDTO = modelMapper.map(message, MessageDTO.class);
		messageDTO.setUserDto(modelMapper.map(message.getUser(), UserDTO.class));
	    return messageDTO;
	}
	
	private List<MessageDTO> convertToListDtos(List<Message> messagesList){
		List<MessageDTO> messageListDtos = new ArrayList<>();
		for (Message message : messagesList) {
			MessageDTO messageDTO = convertToDto(message);
			messageListDtos.add(messageDTO);
		}
		return messageListDtos;
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
