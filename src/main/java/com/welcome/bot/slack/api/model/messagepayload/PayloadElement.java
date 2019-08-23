package com.welcome.bot.slack.api.model.messagepayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"type",
	"text",
	"value",
	"action_id",
	"image_url",
	"alt_text"
})
public class PayloadElement {

	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private PayloadElementText text;
	@JsonProperty("value")
	private String value;
	@JsonProperty("action_id")
	private String action_id;
	@JsonProperty("image_url")
	private String image_url;
	@JsonProperty("alt_text")
	private String alt_text;

	public PayloadElement() {}

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

	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getAlt_text() {
		return alt_text;
	}
	public void setAlt_text(String alt_text) {
		this.alt_text = alt_text;
	}
}