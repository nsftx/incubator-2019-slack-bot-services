package com.welcome.bot.models;

import java.sql.Date;
import java.util.List;

import com.welcome.bot.domain.Choice;

public class PollCreateDTO {
	String title;
	String channelId;
	boolean active;
	Date activeUntil;
	
	List<ChoiceCreateDTO> choiceList;

	public String getTitle() {
		return title;
	}

	public String getChannelId() {
		return channelId;
	}

	public boolean isActive() {
		return active;
	}

	public Date getActiveUntil() {
		return activeUntil;
	}

	public List<ChoiceCreateDTO> getChoiceList() {
		return choiceList;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActiveUntil(Date activeUntil) {
		this.activeUntil = activeUntil;
	}

	public void setChoiceList(List<ChoiceCreateDTO> choiceList) {
		this.choiceList = choiceList;
	}
	




	
}
