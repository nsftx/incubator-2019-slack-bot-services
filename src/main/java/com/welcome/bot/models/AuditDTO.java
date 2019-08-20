package com.welcome.bot.models;

public class AuditDTO {
	
	private Integer auditId;
	private String cause;
	private String consequence;
	

	public Integer getAuditId() {
		return auditId;
	}
	public String getCause() {
		return cause;
	}
	public String getConsequence() {
		return consequence;
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
}
