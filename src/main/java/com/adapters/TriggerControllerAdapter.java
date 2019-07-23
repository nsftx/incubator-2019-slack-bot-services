package com.adapters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domain.Message;
import com.domain.Trigger;
import com.models.TriggerCreateModel;
import com.repository.MessageRepository;
import com.repository.TriggerRepository;

@RestController
public class TriggerControllerAdapter {
	
	@Autowired
	TriggerRepository triggerRepository;
	
	@Autowired
	MessageRepository messageRepository;
	
//    @Autowired
//    private ModelMapper modelMapper;

	//create trigger version 1
	@PostMapping("/api/trigger-ver1")
	public Trigger createTrigger(@RequestBody TriggerCreateModel triggerModel) {
		Trigger trigger = new Trigger();
		Message message = messageRepository.findById(triggerModel.getMessageId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found"));
		
		trigger.setActive(triggerModel.getActive());
		trigger.setChannel(triggerModel.getChannel());
		trigger.setTriggerType(triggerModel.getTriggerType());
		trigger.setMessage(message);
		
		triggerRepository.save(trigger);
		return trigger;
	}
	
	//create trigger 2
	@PostMapping("/api/trigger-ver2")
	public Trigger createTrigger2(@RequestBody TriggerCreateModel triggerModel) {
		ModelMapper modelMapper = new ModelMapper();
		Trigger trigger = modelMapper.map(triggerModel, Trigger.class);
		triggerRepository.save(trigger);
		return trigger;
	}
		
	@PostMapping("/api/trigger-ver3")
	public Trigger createTrigger3(@RequestBody Trigger trigger) {
		Trigger trigger2 = new Trigger();
	
		trigger2.setActive(trigger.getActive());
		trigger2.setChannel(trigger.getChannel());
		trigger2.setTriggerType(trigger.getTriggerType());
		trigger2.setMessage(trigger.getMessage());
		
		triggerRepository.save(trigger2);
		return trigger2;
	}
	
	
	@GetMapping("/api/trigger/{triggerId}")
	public Trigger getTrigger(@PathVariable Integer triggerId) {
		Trigger trigger = triggerRepository.findById(triggerId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trigger not found"));		
		return trigger;		
	}
	
	
	@GetMapping("/api/trigger")
	public Page<Trigger> getAllTriggers(Pageable pageable){
		Page<Trigger> list = triggerRepository.findAll(pageable);
		return list;
	}
	
	
//	@GetMapping("/api/trigger")
//	public List<TriggerGetAllModel> getAllTriggers(@RequestBody Trigger trigger) {
//		List<Trigger> lista = (List<Trigger>) triggerRepository.findAll();
//		//System.out.println(lista.get(0).toString());
//		
//		List<TriggerGetAllModel> modelLista = new ArrayList<TriggerGetAllModel>();
//		for(int i = 0; i < lista.size(); i++) {
//			
////			TriggerGetAllModel triggerGetAllModel = new TriggerGetAllModel(
////															lista.get(i).getTriggerId(),
////															lista.get(i).getMessage().getId(),
////															lista.get(i).getMessage(),
////															lista.get(i).getChannel(), 
////															lista.get(i).getActive()
////															);
//			//modelLista.add(triggerGetAllModel);
//		}
//		
//		
//		return modelLista;
//	}
	
}

