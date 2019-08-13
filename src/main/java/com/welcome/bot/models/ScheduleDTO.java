package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleDTO {
	
	private Integer scheduleId;
	private boolean active;
	private boolean repeat;
	private String channel;
	private Date runAt;
	private Date createdAt;
	
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getRunAt() {
		return runAt;
	}

	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public MessageDTO getMessageDto() {
		return messageDto;
	}

	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}

}
