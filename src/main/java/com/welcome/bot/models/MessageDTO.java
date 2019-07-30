package com.welcome.bot.models;

import java.util.Date;

public class MessageDTO {
	
	Integer messageId;
	String text;
	String title;
	Date createdAt;
	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "MessageDTO [messageId=" + messageId + ", text=" + text + ", title=" + title + ", createdAt=" + createdAt
				+ "]";
	}
	
	
}
