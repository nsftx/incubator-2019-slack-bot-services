package com.welcome.bot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Choice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer choiceDatabaseId;
	
	private Integer choiceId;
	private String choiceValue;
	
	@ManyToOne
	private Poll poll;

	public Choice(Integer choiceId, String choiceValue, Poll poll) {
		this.choiceId = choiceId;
		this.choiceValue = choiceValue;
		this.poll = poll;
	}

	protected Choice() {

	}

	public Integer getChoiceDatabaseId() {
		return choiceDatabaseId;
	}

	public Integer getChoiceId() {
		return choiceId;
	}

	public String getChoiceValue() {
		return choiceValue;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setChoiceDatabaseId(Integer choiceDatabaseId) {
		this.choiceDatabaseId = choiceDatabaseId;
	}

	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}

	public void setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	@Override
	public String toString() {
		return "Choice [choiceDatabaseId=" + choiceDatabaseId + ", choiceId=" + choiceId + ", choiceValue="
				+ choiceValue + ", poll=" + poll + "]";
	}




	
}
