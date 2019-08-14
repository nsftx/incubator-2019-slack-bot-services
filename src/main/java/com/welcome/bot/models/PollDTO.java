package com.welcome.bot.models;

import java.util.List;

import com.welcome.bot.domain.Choice;

public class PollDTO {
	String title;
	String channel;
	List<ChoiceDTO> answerList;
	public String getTitle() {
		return title;
	}
	public String getChannel() {
		return channel;
	}
	public List<ChoiceDTO> getAnswerList() {
		return answerList;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public void setAnswerList(List<ChoiceDTO> answerList) {
		this.answerList = answerList;
	}
	@Override
	public String toString() {
		return "PollDTO [title=" + title + ", channel=" + channel + ", answerList=" + answerList + "]";
	}
	
	

	
}
