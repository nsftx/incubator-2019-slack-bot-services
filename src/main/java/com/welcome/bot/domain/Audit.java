package com.welcome.bot.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auditId;
	private String cause;
	private String consequence;
	
	
	
	public Audit(String cause, String consequence) {
		this.cause = cause;
		this.consequence = consequence;
	}
	
	protected Audit() {
	
	}
	
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
