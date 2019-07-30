package com.welcome.bot.slack.api.model.messagepayloadmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"text",
"elements"
})
public class PayloadBlock {

	// Properties
	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private PayloadBlockText text;
	@JsonProperty("elements")
	private List<PayloadElement> element = null;
	
	// Constructor
	public PayloadBlock() {}

	// Getters/Setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public PayloadBlockText getText() {
		return text;
	}
	public void setText(PayloadBlockText text) {
		this.text = text;
	}

	public List<PayloadElement> getElement() {
		return element;
	}
	public void setElement(List<PayloadElement> element) {
		this.element = element;
	}
}