package com.welcome.bot.slack.api.model.messagepayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"type",
	"text",
	"emoji"
})
public class PayloadElementText {

	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private String text;
	@JsonProperty("emoji")
	private Boolean emoji;

	public PayloadElementText() {}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Boolean getEmoji() {
		return emoji;
	}
	public void setEmoji(Boolean emoji) {
		this.emoji = emoji;
	}
}