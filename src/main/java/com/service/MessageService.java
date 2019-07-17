package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.domain.Message;
import com.repository.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	public @ResponseBody Iterable<Message> getAllMessages() {
		Iterable<Message> messages = messageRepository.findAll();
		return messages;
	}

	public @ResponseBody Message getMessage(Integer id){
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		return message;
	}
	
	public @ResponseBody Message createMessage(Message message){
		message.setCreated_at();
		messageRepository.save(message);
		return message;
	}
	
	public @ResponseBody Iterable<Message> getMessagesByTitle(@RequestParam String title){
		Iterable<Message> lista = messageRepository.getMessagesByTitle(title);
		return lista;
	}
	
	public @ResponseBody Message updateMessage(Integer id, Message message) {
		Message m1 = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		m1.setTitle(message.getTitle());
		m1.setText(message.getText());
		messageRepository.save(m1);
		return m1;
	}

	public Message deleteMessage(Integer id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		messageRepository.delete(message);
		return message;
	}
	
}
