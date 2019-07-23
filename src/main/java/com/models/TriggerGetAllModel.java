package com.models;

import java.util.List;

import com.domain.Message;

public class TriggerGetAllModel {
	Integer triggerId;
	Integer messageId;
	String messageTitle;
	String channel;
	boolean active;
	
	public TriggerGetAllModel() {

	}
	
	public TriggerGetAllModel(Integer triggerId, Integer messageId, String messageTitle, String channel, Boolean active) {
		this.triggerId = triggerId;
		this.messageId = messageId;
		this.messageTitle = messageTitle;
		this.channel = channel;
		this.active = active;
	}
	
	public Integer getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(Integer triggerId) {
		this.triggerId = triggerId;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return messageTitle;
	}
	public void setMessage(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public boolean getActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
}
