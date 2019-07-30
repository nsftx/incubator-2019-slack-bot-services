package com.welcome.bot.models;

import java.util.Date;

public class ScheduleDTO {
	
	private boolean active;
	private boolean repeat;
	private Date runAt;
	private Integer messageId;
	
	public ScheduleDTO(boolean active, boolean repeat, Date runAt, Integer messageId) {
		this.active = active;
		this.repeat = repeat;
		this.runAt = runAt;
		this.messageId = messageId;
	}
	
	protected ScheduleDTO() {
		
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

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}		
}
