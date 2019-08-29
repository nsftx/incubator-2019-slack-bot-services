package com.welcome.bot.models;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.welcome.bot.domain.Choice;

public class PollDTO {
	Integer pollId;
	String title;
	String channelId;
	String channel;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	Date activeUntil;
	
	boolean active;
	List<ChoiceDTO> choiceList;
	
	UserDTO userDTO;

	public Integer getPollId() {
		return pollId;
	}

	public String getTitle() {
		return title;
	}

	public String getChannelId() {
		return channelId;
	}

	public String getChannel() {
		return channel;
	}

	public Date getActiveUntil() {
		return activeUntil;
	}

	public boolean isActive() {
		return active;
	}

	public List<ChoiceDTO> getChoiceList() {
		return choiceList;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setPollId(Integer pollId) {
		this.pollId = pollId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setChoiceList(List<ChoiceDTO> choiceList) {
		this.choiceList = choiceList;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	


	

	
	
}
