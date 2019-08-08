package com.welcome.bot.slack.api.model.channel;

public class Channel {
	
	private String name;
	private String id;
	
	public Channel() {}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}