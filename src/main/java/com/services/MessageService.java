package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.domain.Message;
import com.domain.Schedule;
import com.domain.Trigger;
import com.repository.MessageRepository;
import com.repository.ScheduleRepository;
import com.repository.TriggerRepository;

@Service
public class MessageService {
	
	@Autowired 
	MessageRepository messageRepository;
	
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	ScheduleRepository scheduleRepository;

	public ResponseEntity<Page<Message>> getAllMessages(Pageable pageParam){
		Page<Message> messagePage = messageRepository.findAll(pageParam);
		
		HttpStatus status = HttpStatus.OK;
		if (messagePage.isEmpty()) {
			 status = HttpStatus.NO_CONTENT;
		}
		return ResponseEntity.status(status).body(messagePage);		
	}
	
	public Message getMessage(@PathVariable Integer message_id) {
		Message message = messageRepository.findById(message_id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		return message;
	}
	
	public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder ucb) {
		
		//save message
		message.setCreated_at();
		messageRepository.save(message);
		
		//make location header
		Integer messageId = message.getId();
		UriComponents uriComponents = ucb.path("/api/message/{id}").buildAndExpand(messageId);
							
		//return https status with header "location" and response body
		return ResponseEntity
				.created(uriComponents.toUri())
				.body(message);
	}
	
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		Page<Message> messagesByTitles = messageRepository.findAllByTitle(title, pageParam);
		return messagesByTitles;
	}
	
	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
		Message m1 = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		m1.setTitle(message.getTitle());
		m1.setText(message.getText());
		messageRepository.save(m1);
		return m1;
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
