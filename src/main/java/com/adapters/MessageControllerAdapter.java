package com.adapters;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domain.Message;
import com.repository.MessageRepository;



@RestController
public class MessageControllerAdapter{

	@Autowired
	MessageRepository messageRepository;

	@GetMapping("/api/message")
	public Iterable<Message> getAllMessage(){
		Iterable<Message> messages = messageRepository.findAll();
		return messages;
	}
	
	@GetMapping("/api/message/{id}")
	public Message getMessage(@PathVariable Integer id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		return message;
	}

	@PostMapping("/api/message")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Message createMessage(@RequestBody Message message) {
		message.setCreated_at();
		messageRepository.save(message);
		return message;
	}
	
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Iterable<Message> getMessagesByTitle(@RequestParam String title) {
		Iterable<Message> lista = messageRepository.getMessagesByTitle(title);
		return lista;
	}

	@PutMapping("/api/message/{id}")
	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
		Message m1 = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		m1.setTitle(message.getTitle());
		m1.setText(message.getText());
		messageRepository.save(m1);
		return m1;
	}

	@DeleteMapping("/api/message/{id}")
	public Message deleteMessage(@PathVariable Integer id){
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		messageRepository.delete(message);
		return message;
	}
	
	
	

	//OVO JE SAMO NEKI TEST
//	@GetMapping("/api/testmessage")
//	public @ResponseBody Iterable<Message> getMessagesTest(){
//		//Iterable<Message> messages = messageRepository.findAll();
//		//Ovo sluzi nicemu sad za sad
//		
//		Message msgMessage = new Message();
//		msgMessage.setText("asdas");
//		msgMessage.setTitle("asdas");
//		msgMessage.setCreated_at();
//		
//		Message msgMessage2 = new Message();
//		msgMessage2.setText("test poruke");
//		msgMessage2.setTitle("345");
//		msgMessage2.setCreated_at();
//		
//		List<Message> messages1 = new ArrayList<>();
//		messages1.add(msgMessage);
//		messages1.add(msgMessage2);
//		
//		return messages1;
//	}

//SA SERVISOM
	
	
//	@GetMapping("/api/message")
//	public Iterable<Message> getAllMessage(){
//		return messageService.getAllMessages();
//	}
//	
//	@GetMapping("/api/message/{id}")
//	public Message getMessage(@PathVariable Integer id){
//		return messageService.getMessage(id);
//	}
//	
//	@PostMapping("/api/message")
//	@ResponseStatus(HttpStatus.CREATED)
//	public @ResponseBody Message createMessage(@RequestBody Message message){
//		return messageService.createMessage(message);
//	}
//	
//	@GetMapping("/api/messagesByTitle")
//	public @ResponseBody Iterable<Message> getMessagesByTitle(@RequestParam String title){
//		return messageService.getMessagesByTitle(title);
//	}
//	
//	@PutMapping("/api/message/{id}")
//	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
//		return messageService.updateMessage(id, message);
//	}
	
}

//BEZ SERVISA
//	
//	//get one message
//	@GetMapping("/api/message/{id}")
//	public @ResponseBody Message getMessage(@PathVariable Integer id){
//		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
//		return message;
//	}
//	
//	//get all messages
//	@GetMapping("/api/message")
//	public @ResponseBody Iterable<Message> getAllMessage(){
//		Iterable<Message> messages = messageRepository.findAll();
//		return messages;
//	}
//	
//	//create message
//	@PostMapping("/api/message")
//	public @ResponseBody Message createMessage(@RequestBody Message message){
//		//ovako radi
//		message.setCreated_at();
//		messageRepository.save(message);
//		return message;
//		
//		//da vidimo hoce li ovako
//		//HOCEEEEEE ALI BOLJE JE ONO GORE ZBOG JEDNE LINIJE MANJE
//		//Message m2 = new Message(message.getTitle(), message.getText());
//		//messageRepository.save(m2);
//		//return m2;
//	}
//	
//	//delete message
//	@DeleteMapping("/api/message/{id}")
//	public Message deleteMessage(@PathVariable Integer id) {
//		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "message not found"));
//		messageRepository.deleteById(id);
//		return message;
//	}
//	
//	//update message
//	@PutMapping("/api/message/{id}")
//	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
//		Message m1 = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "message not found"));
//		m1.setTitle(message.getTitle());
//		m1.setText(message.getText());
//		messageRepository.save(m1);
//		return m1;
//	}
//}
