package com.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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

import com.domain.Message;
import com.services.MessageService;



@RestController
public class MessageControllerAdapter{

	
	@Autowired
	MessageService messageService;

	//get all messages
	@GetMapping("/api/messages")
	public ResponseEntity<Page<Message>> getAllMessages(Pageable pageParam){	
		return messageService.getAllMessages(pageParam);
	}
	
	//get selected message
	@GetMapping("/api/messages/{message_id}")
	public Message getMessage(@PathVariable Integer message_id) {
		return messageService.getMessage(message_id);
	}
	
	//create message
	@PostMapping("/api/messages")
	public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder ucb) {
		return messageService.createMessage(message, ucb);
	}
	
	//get messages by title
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		return messageService.getMessagesByTitle(title, pageParam);
	}

	//update message
	@PutMapping("/api/messages/{id}")
	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
		return messageService.updateMessage(id, message);
	}

	//delete message
	@DeleteMapping("/api/messages/{id}")
	public ResponseEntity<Message> deleteMessage(@PathVariable Integer id){
		return messageService.deleteMessage(id);
	}
	
}
