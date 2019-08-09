package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleContentDTO {
	
	private Integer scheduleId;
	private boolean active;
	private boolean repeat;
	private Date runAt;
	@JsonProperty("message")
	private MessageDTO messageDto;
	
	
	public Integer getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
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
	public MessageDTO getMessageDto() {
		return messageDto;
	}
	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}	
	
	

}
