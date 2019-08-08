package com.welcome.bot.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuditSchedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auditId;
	private String channel;
	private String channelInfo;
	
	private Integer scheduleId;
	private String scheduleInfo;

	private String notification;
	

	public AuditSchedule(String channel, String channelInfo, Integer scheduleId, String scheduleInfo) {
		this.channel = channel;
		this.channelInfo = channelInfo;
		this.scheduleId = scheduleId;
		this.scheduleInfo = scheduleInfo;
		this.notification = "Schedule is " + scheduleInfo + 
				"because channel is " + channelInfo;
	}

	protected AuditSchedule() {
	
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(String channelInfo) {
		this.channelInfo = channelInfo;
	}
	
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleInfo() {
		return scheduleInfo;
	}

	public void setScheduleInfo(String scheduleInfo) {
		this.scheduleInfo = scheduleInfo;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}
	

}
