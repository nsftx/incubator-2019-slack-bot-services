package com.welcome.bot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.welcome.bot.models.Language;
import com.welcome.bot.models.Theme;
@Entity
@Table(name = "invite")
public class Invite {
	 @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 @Column(nullable= false)
	 private Boolean sent;
	 public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }
	 public Boolean getSent() {
	        return sent;
	    }

	    public void setSent(Boolean sent) {
	        this.sent = sent;
	    }
	 public Invite() {
		  this.sent=false;
	    }
	 public Invite(Invite invite) {
		  this.id=invite.getId();
		  this.sent=invite.getSent();
		
	    }
}
