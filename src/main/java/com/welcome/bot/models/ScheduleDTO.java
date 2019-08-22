package com.welcome.bot.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ScheduleDTO {
	
	private Integer scheduleId;
	private boolean active;
	private boolean repeat;
	private String channel;
	private String channelId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") 
	private Date runAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") 
	private Date createdAt;
	
	@JsonProperty("message")
	private MessageDTO messageDto;

	@JsonProperty("user")
	private UserDTO userDto;

	public Integer getScheduleId() {
		return scheduleId;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public String getChannel() {
		return channel;
	}

	public String getChannelId() {
		return channelId;
	}

	public Date getRunAt() {
		return runAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public MessageDTO getMessageDto() {
		return messageDto;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setMessageDto(MessageDTO messageDto) {
		this.messageDto = messageDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
	
	
}
