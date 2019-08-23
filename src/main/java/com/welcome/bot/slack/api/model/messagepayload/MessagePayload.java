package com.welcome.bot.slack.api.model.messagepayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"channel",
	"user",
	"post_at",
	"text",
	"time",
	"reminder",
	"scheduled_message_id",
	"ts",
	"attachments"
})
public class MessagePayload {

	@JsonProperty("channel")
	private String channel;
	@JsonProperty("attachments")
	private List<PayloadAttachment> attachments = null;
	@JsonProperty("post_at")
	private String post_at;
	@JsonProperty("user")
	private String user;
	@JsonProperty("text")
	private String text;
	@JsonProperty("time")
	private String time;
	@JsonProperty("scheduled_message_id")
	private String scheduled_message_id;
	@JsonProperty("ts")
	private String ts;

	public MessagePayload() {}

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<PayloadAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<PayloadAttachment> attachments) {
		this.attachments = attachments;
	}

	public String getPostAt() {
		return post_at;
	}
	public void setPostAt(String post_at) {
		this.post_at = post_at;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getScheduledMessageId() {
		return scheduled_message_id;
	}
	public void setScheduledMessageId(String scheduled_message_id) {
		this.scheduled_message_id = scheduled_message_id;
	}

	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
}