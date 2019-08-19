package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TriggerDTO {

	private Integer triggerId;
	private String channelId;
	private String channel;
	private String triggerType;
	private boolean active;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") 
	private Date createdAt;
	
	@JsonProperty("message")
	private MessageDTO messageDto;

	@JsonProperty("user")
	private UserDTO userDto;

	public Integer getTriggerId() {
		return triggerId;
	}

	public String getChannelId() {
		return channelId;
	}

	public String getChannel() {
		return channel;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public boolean isActive() {
		return active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public MessageDTO getMessageDto() {
		return messageDto;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	
	

	
	

}
