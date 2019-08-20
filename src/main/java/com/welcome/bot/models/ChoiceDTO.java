package com.welcome.bot.models;

public class ChoiceDTO {
	Integer choiceId;
	String choiceValue;
	Integer counter;
	
	public Integer getChoiceId() {
		return choiceId;
	}
	public String getChoiceValue() {
		return choiceValue;
	}
	public Integer getCounter() {
		return counter;
	}

	public void setChoiceId(Integer choiceId) {
		this.choiceId = choiceId;
	}
	public void setChoiceValue(String choiceValue) {
		this.choiceValue = choiceValue;
	}
	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
}
