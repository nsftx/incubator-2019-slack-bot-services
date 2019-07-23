package com.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;



@Entity
//@JsonPropertyOrder({"messageId", "title", "text", "createdAt"})
public class Message {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;

	private String title;
	private String text;
	private Date createdAt;
	
	public Message() {
		
	}
	
	public Message(String title, String text) {
		this.title = title;
		this.text = text;
		createdAt = new Date();
	}
	
	public int getId() {
		return messageId;
	}
	public void setId(int messageId) {
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
	public Date getCreated_at() {
		return createdAt;
	}
	public void setCreated_at() {
		//this.created_at = new LocalDateTime().toDate();
		this.createdAt = new Date();
	}	
}
