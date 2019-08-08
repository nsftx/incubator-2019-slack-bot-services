package com.welcome.bot.models;

import java.util.Date;

public class ScheduleCreateDTO {
	
	private boolean active;
	private boolean repeat;
	private Date runAt;
	private String channel;
	private Integer messageId;
	
	
	public ScheduleCreateDTO(boolean active, boolean repeat, Date runAt, String channel, Integer messageId) {
		this.active = active;
		this.repeat = repeat;
		this.runAt = runAt;
		this.channel = channel;
		this.messageId = messageId;
	}
	
	public ScheduleCreateDTO() {

	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	public Date getRunAt() {
		return runAt;
	}
	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	


		
}
