package com.welcome.bot.models;

import java.util.List;

import com.welcome.bot.domain.Choice;

public class PollCreateDTO {
	String title;
	String channel;
	List<ChoiceCreateDTO> answerList;
	
	public String getTitle() {
		return title;
	}
	public String getChannel() {
		return channel;
	}
	public List<ChoiceCreateDTO> getAnswerList() {
		return answerList;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setAnswerList(List<ChoiceCreateDTO> answerList) {
		this.answerList = answerList;
	}
	@Override
	public String toString() {
		return "PollCreateDTO [title=" + title + ", channel=" + channel + ", answerList=" + answerList + "]";
	}
	

	
	

	
}
