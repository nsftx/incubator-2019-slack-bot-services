package com.welcome.bot.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerContentDTO {

	private Integer triggerId;
	private String channel;
	private String triggerType;
	private boolean active;
	@JsonProperty("message")
	private MessageDTO messageDto;
	
	
	public Integer getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public MessageDTO getMessageDto() {
		return messageDto;
	}
	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}
}
