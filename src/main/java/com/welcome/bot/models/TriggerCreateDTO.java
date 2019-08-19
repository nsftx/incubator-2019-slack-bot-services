package com.welcome.bot.models;

public class TriggerCreateDTO {

	private String channelId;
	private String triggerType;
	private boolean active;
	private Integer messageId;
	
	
	public TriggerCreateDTO() {
		
	}


	public String getChannelId() {
		return channelId;
	}


	public String getTriggerType() {
		return triggerType;
	}


	public boolean isActive() {
		return active;
	}


	public Integer getMessageId() {
		return messageId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	

}
