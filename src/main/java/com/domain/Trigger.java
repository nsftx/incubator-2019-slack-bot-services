package com.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity(name = "trigger_tbl")
@JsonPropertyOrder({"triggerId", "channel", "triggerType", "active"})
//@javax.persistence.Table(name = "\"trigger\"")
public class Trigger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer triggerId;
	
	
	private String channel;
	private String triggerType;
	private boolean active;

    //@OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore

	//OBRATI PAZNJU NA OPTIONAL TRUE FALSE
//	@ManyToOne(fetch = FetchType.EAGER, optional = true)
//	@JoinColumn(name = "message_id", nullable = false)
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
    
	
	public Trigger(String channel, String triggerType, boolean active, Message message) {
		this.channel = channel;
		this.triggerType = triggerType;
		this.active = active;
		this.message = message;
	}

	protected Trigger() {
		
	}

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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Trigger [triggerId=" + triggerId + ", channel=" + channel + ", triggerType=" + triggerType + ", active="
				+ active + ", message=" + message + "]";
	}
	
}
