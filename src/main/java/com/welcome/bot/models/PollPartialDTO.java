package com.welcome.bot.models;

import java.util.List;

import com.welcome.bot.domain.Choice;

public class PollPartialDTO {
	Integer pollId;
	String title;
	String channel;
	boolean active;
	List<ChoicePartialDTO> choiceList;
	
	public Integer getPollId() {
		return pollId;
	}
	public String getTitle() {
		return title;
	}
	public String getChannel() {
		return channel;
	}
	public boolean isActive() {
		return active;
	}
	public List<ChoicePartialDTO> getChoiceList() {
		return choiceList;
	}
	public void setPollId(Integer pollId) {
		this.pollId = pollId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setChoiceList(List<ChoicePartialDTO> choiceList) {
		this.choiceList = choiceList;
	}
	

	
}
