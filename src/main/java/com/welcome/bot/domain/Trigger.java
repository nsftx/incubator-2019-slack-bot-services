package com.welcome.bot.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity(name = "trigger_tbl")
@EntityListeners(AuditingEntityListener.class)
@JsonPropertyOrder({
	"triggerId", 
	"channel",
	"triggerType",
	"createdAt",
	"updatedAt",
	"active"})
//@javax.persistence.Table(name = "\"trigger\"")
public class Trigger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer triggerId;

	@Column(nullable=false)
	private String channel;
	@Column(nullable=false)
	private String triggerType;

	private boolean deleted;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;
	
    //@JsonIgnore

	//OBRATI PAZNJU NA OPTIONAL TRUE FALSE
	//@ManyToOne(fetch = FetchType.EAGER, optional = true)
	//@JoinColumn(name = "message_id", nullable = false)
	
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
    	
	public Trigger(String channel, String triggerType, boolean active, Message message) {
		this.channel = channel;
		this.triggerType = triggerType;
		this.active = active;
		this.message = message;
		this.createdAt = new Date();
		this.deleted = false;
	}
	
	protected Trigger() {
	
	}

	public Integer getTriggerId() {
		return triggerId;
	}

	public String getChannel() {
		return channel;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public boolean isActive() {
		return active;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Message getMessage() {
		return message;
	}

	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	
}
