package com.welcome.bot.slack.api.model.messagepayloadmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"text",
"value"
})
public class PayloadElement {

	// Properties
	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private PayloadElementText text;
	@JsonProperty("value")
	private String value;
	
	// Constructor
	public PayloadElement() {}
	
	// Getters/Setters
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public PayloadElementText getText() {
		return text;
	}
	public void setText(PayloadElementText text) {
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}