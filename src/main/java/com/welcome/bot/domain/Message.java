package com.welcome.bot.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity(name = "message")

public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;

	@Column(unique=false, nullable=false, length = 30) 
	@Size(min = 5, max = 30)
	private String title;
	
    @Column(nullable=false, length = 10485760)
    @Size(min = 20, max = 10485760)
	private String text;
    
	private Date createdAt;
	private Date updatedAt;
	private boolean deleted;
	
	
	@ManyToOne
	private User user;
	
	public Message(String title, String text, User user) {
		this.title = title;
		this.text = text;
		this.createdAt = new Date();
		this.updatedAt = null;
		this.user = user;
		this.deleted = false;
	}
	
	protected Message() {
		
	}

	public Integer getMessageId() {
		return messageId;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public User getUser() {
		return user;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}

	public void setDeleted() {
		this.deleted = true;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", title=" + title + ", text=" + text + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deleted=" + deleted + ", user=" + user + "]";
	}
	
}
