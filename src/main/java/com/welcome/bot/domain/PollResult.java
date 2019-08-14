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
	@JoinColumn(name = "pool_id")
	private Poll poll;
	
	private String choice;
	private String choiceId;
	
	public PollResult(Integer poolResultId, Poll poll, String choice, String choiceId) {
		this.poll = poll;
		this.choice = choice;
		this.choiceId = choiceId;
	}
	
	public Integer getPoolResultId() {
		return poolResultId;
	}
	public Poll getPoll() {
		return poll;
	}
	public String getChoice() {
		return choice;
	}
	public String getChoiceId() {
		return choiceId;
	}
	public void setPoolResultId(Integer poolResultId) {
		this.poolResultId = poolResultId;
	}
	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	public void setChoice(String choice) {
		this.choice = choice;
	}
	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}
	
	
	
}
