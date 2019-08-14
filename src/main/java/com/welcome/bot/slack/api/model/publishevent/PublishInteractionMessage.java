package com.welcome.bot.slack.api.model.publishevent;

import java.util.UUID;

public class PublishInteractionMessage {
	
	private String channel;
	private String choiceSelected;
	private String choiceID;
	private UUID pollID;
	private String user;
	
	// TODO - TEST/DELETE
	private String text;
	private String timestamp;

	public PublishInteractionMessage() {}


	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChoiceSelected() {
		return choiceSelected;
	}
	public void setChoiceSelected(String choiceSelected) {
		this.choiceSelected = choiceSelected;
	}

	public String getChoiceID() {
		return choiceID;
	}
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}

	public UUID getPollID() {
		return pollID;
	}
	public void setPollID(UUID pollID) {
		this.pollID = pollID;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	// TODO - TEST/DELETE
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}