package com.ports;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.domain.Message;

public interface MessageControllerPort {
	@GetMapping("/api/message")
	public Iterable<Message> getAllMessage();
	
	@GetMapping("/api/message/{id}")
	public Message getMessage(@PathVariable Integer id);
	
	@PostMapping("/api/message")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Message createMessage(@RequestBody Message message);
	
	@PutMapping("/api/message/{id}")
	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message);
	
	@DeleteMapping("/api/message/{id}")
	public Message deleteMessage(@PathVariable Integer id);
	
	//CUSTOM METHOD
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Iterable<Message> getMessagesByTitle(@RequestParam String title);
	

}
