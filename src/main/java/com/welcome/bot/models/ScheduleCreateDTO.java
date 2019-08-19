package com.welcome.bot.models;

import java.util.Date;

public class ScheduleCreateDTO {
	
	private boolean active;
	private boolean repeat;
	private Date runAt;
	private String channelId;
	private String intervalId;
	private Integer messageId;
	
	public ScheduleCreateDTO(boolean active, boolean repeat, Date runAt, String channelId, String intervalId,
			Integer messageId) {
		super();
		this.active = active;
		this.repeat = repeat;
		this.runAt = runAt;
		this.channelId = channelId;
		this.intervalId = intervalId;
		this.messageId = messageId;
	}
	
	public ScheduleCreateDTO() {

	}
	
	public boolean isActive() {
		return active;
	}
	public boolean isRepeat() {
		return repeat;
	}
	public Date getRunAt() {
		return runAt;
	}
	public String getChannelId() {
		return channelId;
	}
	public String getIntervalId() {
		return intervalId;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public void setIntervalId(String intervalId) {
		this.intervalId = intervalId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	




		
}
