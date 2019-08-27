package com.welcome.bot.models;

import java.sql.Date;

public class AuditDTO {
	
	private Integer auditId;
	private String cause;
	private String consequence;
	private Date createdAt;
	
	public Integer getAuditId() {
		return auditId;
	}
	public String getCause() {
		return cause;
	}
	public String getConsequence() {
		return consequence;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public void setConsequence(String consequence) {
		this.consequence = consequence;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
