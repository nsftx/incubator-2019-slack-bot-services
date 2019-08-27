package com.welcome.bot.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.engine.query.spi.sql.NativeSQLQueryCollectionReturn;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer scheduleId;
	
	private Boolean active;
	
	@Column(name = "`repeat`") 
	private Boolean repeat;
	private Boolean deleted;
	private String intervalType;
	private Date runAt;
	private String channel;
	private String channelId;
	private String slackScheduleId;
	private Date createdAt;
	private Date updatedAt;
	

	@ManyToOne 
	private User user;

	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
	
	public Schedule(Boolean active, Boolean repeat, 
			Date runAt,
			String channel,
			String channelId, 
			User user,
			Message message) {

		this.active = active;
		this.repeat = repeat;
		this.runAt = runAt;
		this.channel = channel;
		this.channelId = channelId;
		this.createdAt = new Date();
		this.deleted = false;
		this.user = user;
		this.message = message;
	}

	protected Schedule() {
		
	}

	public Integer getScheduleId() {
		return scheduleId;
	}



	public Boolean getActive() {
		return active;
	}



	public Boolean getRepeat() {
		return repeat;
	}



	public Boolean getDeleted() {
		return deleted;
	}



	public String getIntervalType() {
		return intervalType;
	}



	public Date getRunAt() {
		return runAt;
	}



	public String getChannel() {
		return channel;
	}



	public String getChannelId() {
		return channelId;
	}



	public String getSlackScheduleId() {
		return slackScheduleId;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public User getUser() {
		return user;
	}




	public Message getMessage() {
		return message;
	}



	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}



	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}



	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}



	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}



	public void setChannel(String channel) {
		this.channel = channel;
	}



	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}



	public void setSlackScheduleId(String slackScheduleId) {
		this.slackScheduleId = slackScheduleId;
	}



	public void setCreatedAt() {
		this.createdAt = new Date();
	}



	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}



	public void setUser(User user) {
		this.user = user;
	}



	public void setMessage(Message message) {
		this.message = message;
	}



	
	
}


