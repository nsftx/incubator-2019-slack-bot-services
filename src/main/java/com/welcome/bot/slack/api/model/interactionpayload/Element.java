package com.welcome.bot.slack.api.model.interactionpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"type",
	"action_id",
	"text",
	"value"
})
public class Element {

	@JsonProperty("type")
	private String type;
	@JsonProperty("action_id")
	private String actionId;
	@JsonProperty("text")
	private Text text;
	@JsonProperty("value")
	private String value;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}