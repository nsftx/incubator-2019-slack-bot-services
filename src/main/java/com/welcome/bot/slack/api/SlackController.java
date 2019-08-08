package com.welcome.bot.slack.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.welcome.bot.slack.api.model.eventpayload.EventPayload;

@Controller
public class SlackController {
	
	String eventTimeStamp;
	
	@Autowired
	SlackEventService slackEventService;
	@Autowired
	SlackCommandService slackCommandService;
	@Autowired
	SlackInteractionService slackInteractionService;

	@PostMapping("/api/slimp/incoming-event-hook")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String eventHandler(@RequestBody EventPayload event) {
		// check here for event type, if not url_verif. return null immediately
		if (eventTimeStamp == null) {
			eventTimeStamp = event.getEventItem().getEventTs();
			return slackEventService.handleEvent(event);
		}
		
		if(!event.getEventItem().getEventTs().equals(eventTimeStamp)) {
			eventTimeStamp = event.getEventItem().getEventTs();
			return slackEventService.handleEvent(event);
		} else {
			return null;
		}
	}

	@PostMapping("/api/slimp/incoming-command-about")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandAbout() {
		return slackCommandService.handleCommand("about");
	}
	
	@PostMapping("/api/slimp/incoming-command-benefits")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandBenefits() {
		return slackCommandService.handleCommand("benefits");
	}
	
	@PostMapping("/api/slimp/incoming-command-work")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandWork() {
		return slackCommandService.handleCommand("work");
	}
	
	@PostMapping("/api/slimp/incoming-command-docs")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandDocs() {
		return slackCommandService.handleCommand("docs");
	}
	
	@PostMapping("/api/slimp/incoming-command-community")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandCommunity() {
		return slackCommandService.handleCommand("community");
	}
	
	@PostMapping("/api/slimp/incoming-action-interaction")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String interactionHandler(@RequestBody String data/*InteractionPayload interactionPayload*/) {
		System.out.println("HELLO INTERACTION. DEMO TEST!!! DATA : " + data);
		
		// convert to JSON
		
		return null;
	}
}