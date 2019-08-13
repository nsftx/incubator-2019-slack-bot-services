package com.welcome.bot.domain;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@Entity(name="schedule_tbl")
@JsonPropertyOrder({"scheduleId", "active", "repeat", "runAt"})
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer scheduleId;
	
	private Boolean active;
	
	@Column(name = "`repeat`") 
	private Boolean repeat;
	private Date runAt;
	private String channel;
	private String slackScheduleId;
	private Date createdAt;
	private Date updatedAt;
	
	
	@ManyToOne
	@JoinColumn(name = "message_id")
	private Message message;
	
	public Schedule(Boolean active, Boolean repeat, Date runAt, String channel, Message message) {
		this.active = active;
		this.repeat = repeat;
		this.runAt = runAt;
		this.channel = channel;
		this.message = message;
		this.createdAt = new Date();
	}
	
	public Schedule() {

	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}

	public Date getRunAt() {
		return runAt;
	}

	public void setRunAt(Date runAt) {
		this.runAt = runAt;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSlackScheduleId() {
		return slackScheduleId;
	}

	public void setSlackScheduleId(String slackScheduleId) {
		this.slackScheduleId = slackScheduleId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	
	
}


