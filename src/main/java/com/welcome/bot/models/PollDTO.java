package com.welcome.bot.models;

public class PollDTO {
	String title;
	String text;
	String channel;
	
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public String getChannel() {
		return channel;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
