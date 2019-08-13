package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerDTO {

	private Integer triggerId;
	private String channel;
	private String triggerType;
	private boolean active;
	private Date createdAt;
	
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public MessageDTO getMessageDto() {
		return messageDto;
	}

	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}
	
	

}
