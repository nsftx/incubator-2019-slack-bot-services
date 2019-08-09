package com.welcome.bot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.welcome.bot.domain.Message;
import com.welcome.bot.exception.BadRequestException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.services.MessageService;



@RestController
public class MessageController{

	
	@Autowired
	MessageService messageService;

	//get all messages
    
	@GetMapping("/api/messages")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public Page<MessageDTO> getAllMessages(Pageable pageParam){	
		return messageService.getAllMessages(pageParam);
	}
	
//	@GetMapping("/api/messages2")
//	public ResponseEntity<Page<Message>> getAllMessages2(Pageable pageParam, @PathParam(value = "title") String title){	
//		
//		
//		return messageService.getAllMessages2(pageParam, title);
//	}
	
	//get selected message
	@GetMapping("/api/messages/{message_id}")
	public MessageDTO getMessage(@PathVariable Integer message_id) {
		return messageService.getMessage(message_id);
	}
	
	//create message
	@PostMapping("/api/messages")
	public @ResponseBody ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageModel, UriComponentsBuilder ucb) {
		return messageService.createMessage(messageModel, ucb);
	}
	
	
	//get messages by title
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		return messageService.getMessagesByTitle(title, pageParam);
	}

	//update message
	@PutMapping("/api/messages/{id}")
	public MessageDTO updateMessage(@PathVariable Integer id, @RequestBody MessageDTO message) {
		return messageService.updateMessage(id, message);
	}

	//delete message
	@DeleteMapping("/api/messages/{id}")
	public ResponseEntity<Message> deleteMessage(@PathVariable Integer id){
		return messageService.deleteMessage(id);
	}	
	
}
