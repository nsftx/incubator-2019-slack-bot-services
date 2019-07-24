package com.models;

public class TriggerDTO {

	private String channel;
	private String triggerType;
	private boolean active;
	private Integer messageId;
	
	public TriggerDTO() {
		
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
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
}
//
//
//
//
//private String channel;
//private String triggerType;
//private boolean active;
//private Integer messageId;
