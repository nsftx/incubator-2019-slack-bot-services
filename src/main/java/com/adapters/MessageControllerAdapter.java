package com.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.domain.Message;
import com.domain.Trigger;
import com.repository.MessageRepository;
import com.repository.TriggerRepository;



@RestController
public class MessageControllerAdapter{

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	TriggerRepository triggerRepository;

	//get all messages
	@GetMapping("/api/message")
	public ResponseEntity<Page<Message>> getAllMessage(Pageable pageParam){
		Page<Message> messagePage = messageRepository.findAll(pageParam);
		
		HttpStatus status = HttpStatus.OK;
		if (messagePage.isEmpty()) {
			 status = HttpStatus.NO_CONTENT;
		}
		return ResponseEntity.status(status).body(messagePage);		
	}
	
	//get selected message
	@GetMapping("/api/message/{id}")
	public Message getMessage(@PathVariable Integer id) {
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		return message;
	}
	
	//create message
	@PostMapping("/api/message")
	//@ResponseStatus(HttpStatus.CREATED)
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
	
	
	//CUSTOM get messages by title
	@GetMapping("/api/messagesByTitle")
	public @ResponseBody Page<Message> getMessagesByTitle(@RequestParam String title, Pageable pageParam) {
		Page<Message> messagesByTitles = messageRepository.findAllByTitle(title, pageParam);
		return messagesByTitles;
	}

	//update message
	@PutMapping("/api/message/{id}")
	public Message updateMessage(@PathVariable Integer id, @RequestBody Message message) {
		Message m1 = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		m1.setTitle(message.getTitle());
		m1.setText(message.getText());
		messageRepository.save(m1);
		return m1;
	}

	//delete message
	@DeleteMapping("/api/message/{id}")
	public Message deleteMessage(@PathVariable Integer id){
		Message message = messageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message data not found"));
		
		//delete all triggers connected with messages
		List<Trigger> listaTrigera = triggerRepository.findAllByMessage(message);
		triggerRepository.deleteAll(listaTrigera);
		
		System.out.println(listaTrigera);
		
		
		messageRepository.delete(message);
		return message;
	}
	
	
	//CUSTOM METHOD for getting pageableeeee stuffffffffffffffff
//	@GetMapping("message")
//	public ResponseEntity<MessageModel> customGetMessage(Pageable pageable) {
//
//		List<Message> lista = messageRepository.findAll();
//		
//		HttpStatus status = HttpStatus.OK;
//		if (lista.isEmpty()) {
//			 status = HttpStatus.NO_CONTENT;
//		}
//		
//		MessageModel messageModel = new MessageModel();
//		messageModel.setMessages(lista);
//		messageModel.setPage(pageable.getPageNumber());
//		messageModel.setSize(pageable.getPageSize());
//		
//		return ResponseEntity.status(status).body(messageModel);
//	}
	
//---------------------------------------------------------------------------------------------------------	
//	
//	//SOME CUSTOM METHODS
//	
//	@GetMapping("/response-entity-builder-with-http-headers")
//	public ResponseEntity<String> usingResponseEntityBuilderAndHttpHeaders() {
//	    HttpHeaders responseHeaders = new HttpHeaders();
//	    responseHeaders.set("Baeldung-Example-Header", 
//	      "Value-ResponseEntityBuilderWithHttpHeaders");
//	 
//	    return ResponseEntity.ok()
//	      .headers(responseHeaders)
//	      .body("Response with header using ResponseEntity");
//	}
//	
//	@PostMapping("/api/message-custom")
//	public @ResponseBody ResponseEntity<Message> createMessageCustom(@RequestBody Message message, UriComponentsBuilder ucb) {
//		message.setCreated_at();
//		messageRepository.save(message);
//		
//		Integer id = message.getId();
//		UriComponents uriComponents = ucb.path("/message/{id}").buildAndExpand(id);
//		
//		//
//		HttpHeaders responseHeaders = new HttpHeaders();
//		responseHeaders.set(HttpHeaders.AGE, "2");
//		//
//		
//		responseHeaders.setLocation(uriComponents.toUri());
//
//		return ResponseEntity.created(uriComponents.toUri()).headers(responseHeaders).body(message);
//	}
//	
	

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


//CUSTOM METODE KOJE NE SLUZE NICEMU

//@GetMapping("/api/message-custom-method-2")
//public JSONObject customMethod2(Pageable pageParam) {
//	List<Message> lista = new ArrayList<Message>();
//	lista.add(new Message("asdas", "asdasdas"));
//	lista.add(new Message("asd12131", "asd121"));
//	lista.add(new Message("asd123123123123", "1231"));	
//	PayLoad<Message> payLoad = new PayLoad<Message>();
//	payLoad.setContent(lista);
//	payLoad.setSize(lista.size());
//
//	System.out.println(payLoad);
//	return payLoad;
//	JSONObject jo = new JSONObject();
//	try {
//		jo.put("content", lista);
//		jo.put("age", "22");
//		jo.put("city", "chicago");
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	
//
//	return null;
//}


//@GetMapping("cars")
//public Car metoda() {
//	ObjectMapper objectMapper = new ObjectMapper();
//
//	String carJson = "{ \"brand\" : \"Mercedes\", \"doors\" : 5 }";
//
//	Car car = null;
//	try {
//	    car = objectMapper.readValue(carJson, Car.class);
//
//	    System.out.println("car brand = " + car.getBrand());
//	    System.out.println("car doors = " + car.getDoors());
//	} catch (IOException e) {
//	    e.printStackTrace();
//	}
//	return car;
//}

//@GetMapping("/api/message-custom-method")
//public Page<Message> customMethod(Pageable pageParam) {
//	Message message1 = messageRepository.findById(1).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//	Page<Message> messagePage = messageRepository.findAll(pageParam);
//	
//	String json = gson.toJson(message1);
//	List<String> lista = new ArrayList<>();
//
//	
//	
//	for(Message eMessage : messagePage) {
//		lista.add(gson.toJson(eMessage));
//		System.out.println(eMessage);
//	}
//
//	Message message = gson.fromJson(json, Message.class);
//	
//	return messagePage;
//}

//@GetMapping("/api/message")
//public Page<Message> getAllMessage(Pageable pageParam){
	
//	Page<Message> messagePage = messageRepository.findAll(pageParam);	
	
//	return messagePage;		
	
//	List<Message> messagesList = messagePage.getContent();
//	List<MessageGson> msgJsonGsons = new ArrayList<>();
//	
//	for(Message eMessage : messagesList) {
//		MessageGson gs = new MessageGson();
//		gs.setCreatedAt(eMessage.getCreated_at());
//		//gs.setMessageId(eMessage.getId());
//		gs.setText(eMessage.getText());
//		gs.setTitle(eMessage.getTitle());
//		msgJsonGsons.add(gs);
//	}
//	
//	jMsg.setTitle("messages");
//	jMsg.setMessages(messagesList);
//	
//	String json = gson.toJson(jMsg);
//	
//	
//	return json;
//}

