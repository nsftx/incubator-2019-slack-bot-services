package com.welcome.bot.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

@Entity
@Table(indexes = { @Index(name = "IX_choiceId_pollId", columnList = "choiceId, pollId") })
public class PollResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer pollResultId;
	
	private String userId;
	private Integer choiceId;
	
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID pollId;
	
	public PollResult(String userId, Integer choiceId, UUID pollId) {
		this.userId = userId;
		this.choiceId = choiceId;
		this.pollId = pollId;
	}
	
	protected PollResult() {
		
	}

	public Integer getPollResultId() {
		return pollResultId;
	}

	public String getUserId() {
		return userId;
	}

	public Integer getChoiceId() {
		return choiceId;
	}

	public UUID getPollId() {
		return pollId;
	}

	public void setPollResultId(Integer pollResultId) {
		this.pollResultId = pollResultId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}

	public void setPollId(UUID pollId) {
		this.pollId = pollId;
	}

	@Override
	public String toString() {
		return "PollResult [pollResultId=" + pollResultId + ", userId=" + userId + ", choiceId=" + choiceId
				+ ", pollId=" + pollId + "]";
	}

	
	
	
}
