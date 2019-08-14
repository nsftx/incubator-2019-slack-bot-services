package com.welcome.bot.slack.api.model.interactionpayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"subtype",
"text",
"ts",
"username",
"bot_id",
"attachments"
})
public class Message {

	@JsonProperty("type")
	private String type;
	@JsonProperty("subtype")
	private String subtype;
	@JsonProperty("text")
	private String text;
	@JsonProperty("ts")
	private String ts;
	@JsonProperty("username")
	private String username;
	@JsonProperty("bot_id")
	private String botId;
	@JsonProperty("attachments")
	private List<Attachment> attachments = null;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getBotId() {
		return botId;
	}
	public void setBotId(String botId) {
		this.botId = botId;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
}