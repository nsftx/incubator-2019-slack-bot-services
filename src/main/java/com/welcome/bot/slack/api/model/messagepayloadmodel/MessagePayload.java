package com.welcome.bot.slack.api.model.messagepayloadmodel;

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
"attachments"
})
public class MessagePayload {

	// Properties
	@JsonProperty("channel")
	public String channel;
	@JsonProperty("attachments")
	public List<PayloadAttachment> attachments = null;
	
	// Additional property - for SCHEDULES
	@JsonProperty("post_at")
	public String post_at;
	
	// Additional property - for EPHEMERAL POST & REMINDER
	@JsonProperty("user")
	public String user;
	
	// Additional property - for REMINDER
	@JsonProperty("text")
	public String text;
	
	@JsonProperty("time")
	public String time;
	
	// Additional property - for REMINDER DELETE
	@JsonProperty("reminder")
	public String reminder;
	
	// Additional property - for SCHEDULE DELETE
	@JsonProperty("scheduled_message_id")
	public String scheduled_message_id;
	
	// Constructor
	public MessagePayload() {}
	
	// Getters/Setters
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
	
	// Additional getters/setters for post_at (for SCHEDULES)
	public String getPostAt() {
		return post_at;
	}
	public void setPostAt(String post_at) {
		this.post_at = post_at;
	}
	
	// Additional getters/setters for user (for EPHEMERAL POST & REMINDER)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	// Additional getters/setters for text & time (for REMINDER)
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

	// Additional getter/setter for reminder (for REMINDER DELETE)
	public String getReminder() {
		return reminder;
	}
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	// Additional getter/setter for schedule (for SCHEDULE DELETE)
	public String getScheduledMessageId() {
		return scheduled_message_id;
	}
	public void setScheduledMessageId(String scheduled_message_id) {
		this.scheduled_message_id = scheduled_message_id;
	}
	
	
}