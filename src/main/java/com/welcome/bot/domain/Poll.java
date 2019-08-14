package com.welcome.bot.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Poll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer poolId;
	
	private String title;
	private String text;
	private String channel;
	private UUID slackPoolId;
	
	public Poll(String title, String text, String channel) {
		this.title = title;
		this.text = text;
		this.channel = channel;
	}
	protected Poll() {

	}
	public Integer getPoolId() {
		return poolId;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	public String getChannel() {
		return channel;
	}
	public UUID getSlackPoolId() {
		return slackPoolId;
	}
	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
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
	public void setSlackPoolId(UUID slackPoolId) {
		this.slackPoolId = slackPoolId;
	}
	
	
	
}
