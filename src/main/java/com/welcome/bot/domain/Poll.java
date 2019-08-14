package com.welcome.bot.domain;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Poll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pollId;
	
	private String title;
	private String channel;
	private UUID pollUuid;
	
	public Poll(String title, String channel) {
		this.title = title;
		this.channel = channel;
	}
	
	protected Poll() {
	
	}
	
	public Integer getPollId() {
		return pollId;
	}

	public String getTitle() {
		return title;
	}

	public String getChannel() {
		return channel;
	}

	public UUID getPollUuid() {
		return pollUuid;
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

	public void setPollUuid(UUID pollUuid) {
		this.pollUuid = pollUuid;
	}

	@Override
	public String toString() {
		return "Poll [pollId=" + pollId + ", title=" + title + ", channel=" + channel + ", pollUuid=" + pollUuid + "]";
	}
	
	
	
	
}
