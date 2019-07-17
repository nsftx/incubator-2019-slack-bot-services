package com.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Message;
import com.ports.MessageControllerPort;
import com.service.MessageService;



@RestController
public class MessageControllerAdapter implements MessageControllerPort{

	@Autowired
	MessageService messageService;

	@Override
	public Iterable<Message> getAllMessage() {
		return messageService.getAllMessages();
	}
	
	@Override
	public Message getMessage(Integer id) {
		return messageService.getMessage(id);
	}

	@Override
	public Message createMessage(Message message) {
		return messageService.createMessage(message);
	}

	@Override
	public Iterable<Message> getMessagesByTitle(String title) {
		return messageService.getMessagesByTitle(title);
	}

	@Override
	public Message updateMessage(Integer id, Message message) {
		return messageService.updateMessage(id, message);
	}

	@Override
	public Message deleteMessage(Integer id) {
		return messageService.deleteMessage(id);
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
