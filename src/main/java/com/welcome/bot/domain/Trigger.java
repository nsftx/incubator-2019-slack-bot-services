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

	private String channel;
	private String channelId;
	
	@Column(nullable=false)
	private String triggerType;

	private boolean deleted;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;

	

	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
    
	@ManyToOne
	private User user;

	
	public Trigger(String channel, String channelId, String triggerType,
			boolean active, Message message, User user) {
		this.channel = channel;
		this.channelId = channelId;
		this.triggerType = triggerType;
		this.active = active;
		this.createdAt = new Date();
		this.message = message;
		this.user = user;
	}
	
	public Trigger() {}



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

	public User getUser() {
		return user;
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

	public void setUser(User user) {
		this.user = user;
	}
	

	
	
	
}
