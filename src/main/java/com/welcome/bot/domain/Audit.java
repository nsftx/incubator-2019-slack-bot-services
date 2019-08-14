package com.welcome.bot.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
@JsonPropertyOrder({
"auditId",
"channel",
"channelInfo",
"entityId",
"entity",
"entityInfo",
"notification"
})
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auditId;
	private String channel;
	private String channelInfo;
	
	private Integer entityId;
	private String entity;
	private String entityInfo;

	private String notification;
	

	public Audit(String channel, String channelInfo, Integer entityId, String entity, String entityInfo) {
		this.channel = channel;
		this.channelInfo = channelInfo;
		this.entityId = entityId;
		this.entity = entity;
		this.entityInfo = entityInfo;
		this.notification = entity + "with id: " + entityId + " is " + entityInfo + 
				"because channel " + channel + " is " + channelInfo;
	}

	protected Audit() {
	
	}

	public Integer getAuditId() {
		return auditId;
	}

	public String getChannel() {
		return channel;
	}

	public String getChannelInfo() {
		return channelInfo;
	}

	public Integer getEntityId() {
		return entityId;
	}

	public String getEntity() {
		return entity;
	}

	public String getEntityInfo() {
		return entityInfo;
	}

	public String getNotification() {
		return notification;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setChannelInfo(String channelInfo) {
		this.channelInfo = channelInfo;
	}

	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public void setEntityInfo(String entityInfo) {
		this.entityInfo = entityInfo;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}



}
