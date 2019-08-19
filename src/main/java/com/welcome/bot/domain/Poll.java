package com.welcome.bot.domain;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

@Entity
public class Poll {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pollId;
	
	private String title;
	private String channel;
	private boolean active;
	private Date createdAt;
	private Date updatedAt;
	
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID pollUuid;
	
	public Poll(String title, String channel, boolean active) {
		this.title = title;
		this.channel = channel;
		this.active = active;
		this.createdAt = new Date();
		this.pollUuid = UUID.randomUUID();
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
	public boolean isActive() {
		return active;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
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
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setCreatedAt() {
		this.createdAt =  new Date();;
	}
	public void setUpdatedAt() {
		this.updatedAt =  new Date();;
	}
	public void setPollUuid(UUID pollUuid) {
		this.pollUuid = pollUuid;
	}
	@Override
	public String toString() {
		return "Poll [pollId=" + pollId + ", title=" + title + ", channel=" + channel + ", active=" + active
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", pollUuid=" + pollUuid + "]";
	}
}
