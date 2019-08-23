package com.welcome.bot.domain;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Audit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer auditId;
	private String cause;
	private String consequence;
	private boolean seen;
	private Date createdAt;
	
	@ManyToOne
	private User user;
	
	public Audit(String cause, String consequence, User user) {
		this.cause = cause;
		this.consequence = consequence;
		this.user = user;
		this.seen = false;
		this.createdAt = new Date();
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
	
	public boolean getSeen() {
		return seen;
	}

	public User getUser() {
		return user;
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

	public void setSeen() {
		this.seen = true;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	public void setCreatedAt() {
		this.createdAt = new Date();
	}

	
	
}
