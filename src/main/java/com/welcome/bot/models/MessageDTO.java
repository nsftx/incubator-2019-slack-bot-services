package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO {
	
	Integer messageId;
	String text;
	String title;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") 
	Date createdAt;
	
	@JsonProperty("user")
	private UserDTO userDto;

	public Integer getMessageId() {
		return messageId;
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
	
	
	
	
}
