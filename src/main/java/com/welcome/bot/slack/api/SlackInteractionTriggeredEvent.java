package com.welcome.bot.slack.api;

import org.springframework.context.ApplicationEvent;

import com.welcome.bot.slack.api.model.publishevent.PublishInteractionMessage;

@SuppressWarnings("serial")
public class SlackInteractionTriggeredEvent extends ApplicationEvent {

	private PublishInteractionMessage interactionData = new PublishInteractionMessage();

	public SlackInteractionTriggeredEvent(Object source, PublishInteractionMessage interactionData) {
		super(source);
		this.interactionData = interactionData;
	}

	public PublishInteractionMessage getInteractionData() {
		return interactionData;
	}
}