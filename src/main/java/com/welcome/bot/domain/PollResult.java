package com.welcome.bot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PollResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer poolResultId;
	
	@ManyToOne
	private Choice choice;
	
	public PollResult(Choice choice) {
		this.choice = choice;
	}
	public PollResult() {

	}
	
	public Integer getPoolResultId() {
		return poolResultId;
	}
	public Choice getChoice() {
		return choice;
	}
	public void setPoolResultId(Integer poolResultId) {
		this.poolResultId = poolResultId;
	}
	public void setChoice(Choice choice) {
		this.choice = choice;
	}
	
	

	
	
}
