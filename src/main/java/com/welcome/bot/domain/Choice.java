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
	private Integer choiceId;
	
	private String answer;
	
	@ManyToOne
	private Poll poll;

	public Choice(String answer, Poll poll) {
		this.answer = answer;
		this.poll = poll;
	}

	protected Choice() {

	}

	public Integer getChoiceId() {
		return choiceId;
	}

	public String getAnswer() {
		return answer;
	}

	public Poll getPoll() {
		return poll;
	}

	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}

	@Override
	public String toString() {
		return "Choice [choiceId=" + choiceId + ", answer=" + answer + ", poll=" + poll + "]";
	}
}
