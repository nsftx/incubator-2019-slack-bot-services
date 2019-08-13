package com.welcome.bot.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity(name = "message")
@JsonPropertyOrder({
	"messageId", 
	"title",
	"text",
	"createdAt,"
	+ " updatedAt"})
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;

	@Column(unique=true, nullable=false, length = 30) 
	@Size(min = 5, max = 30)
	private String title;
	
    @Column(nullable=false, length = 10485760)
    @Size(min = 20, max = 10485760)
	private String text;
    
	private Date createdAt;
	private Date updatedAt;
	
	public Message(String title, String text) {
		this.title = title;
		this.text = text;
		createdAt = new Date();
		updatedAt = null;
	}
	
	protected Message() {
		
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + ", title=" + title + ", text=" + text + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}	
}
