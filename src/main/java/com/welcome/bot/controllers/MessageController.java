package com.welcome.bot.controllers;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.models.MessageCreateDTO;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.repository.MessageRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.services.MessageService;

import javassist.compiler.ast.NewExpr;



@RestController
public class MessageController{

	
	@Autowired
	MessageService messageService;

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserRepository userRepository;

	
	//get all messages
    
	@GetMapping("/api/messages")
	public Page<MessageDTO> getAllMessages(Pageable pageParam){	
		return messageService.getAllMessages(pageParam);	
	}
	
	//get selected message
	@GetMapping("/api/messages/{message_id}")
	public MessageDTO getMessage(@PathVariable Integer message_id) {
		return messageService.getMessage(message_id);
	}
	
	//create message
	@PostMapping("/api/messages")
	public @ResponseBody MessageDTO createMessage(@RequestBody MessageCreateDTO messageModel) {
		return messageService.createMessage(messageModel);
	}
	
	//get messages by title
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		return messageService.getMessagesByTitle(title, pageParam);
	}

	//update message
	@PutMapping("/api/messages/{id}")
	public MessageDTO updateMessage(@PathVariable Integer id, @RequestBody MessageCreateDTO message) {
		return messageService.updateMessage(id, message);
	}

	//delete message
	@DeleteMapping("/api/messages/{id}")
	public ResponseEntity<MessageDTO> deleteMessage(@PathVariable Integer id){
		return messageService.deleteMessage(id);
	}	

	
}
