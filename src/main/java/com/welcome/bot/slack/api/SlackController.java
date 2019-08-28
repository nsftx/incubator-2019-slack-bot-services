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

	@Autowired
	SlackEventService slackEventService;
	@Autowired
	SlackCommandService slackCommandService;
	@Autowired
	SlackInteractionService slackInteractionService;

	@PostMapping("/slack-api/incoming-event-hook")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String eventHandler(@RequestBody EventPayload event) {
		if(event.getType().equals("url_verification")) {
			return slackEventService.handleEvent(event);
		} else {
			slackEventService.handleEvent(event);
			return null;
		}
	}

	@PostMapping("/slack-api/incoming-command-about")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandAbout() {
		return slackCommandService.handleCommand("about");
	}

	@PostMapping("/slack-api/incoming-command-benefits")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandBenefits() {
		return slackCommandService.handleCommand("benefits");
	}

	@PostMapping("/slack-api/incoming-command-work")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandWork() {
		return slackCommandService.handleCommand("work");
	}

	@PostMapping("/slack-api/incoming-command-docs")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandDocs() {
		return slackCommandService.handleCommand("docs");
	}

	@PostMapping("/slack-api/incoming-command-community")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String commandCommunity() {
		return slackCommandService.handleCommand("community");
	}

	@PostMapping("/slack-api/incoming-action-interaction")
	@ResponseStatus(HttpStatus.OK)
	public void interactionHandler(@RequestBody String interactionRequestPayload) {
		slackInteractionService.handleInteraction(interactionRequestPayload);
	}
}