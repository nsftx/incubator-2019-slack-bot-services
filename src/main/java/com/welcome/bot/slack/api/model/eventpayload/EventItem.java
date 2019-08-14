package com.welcome.bot.slack.api.model.eventpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"client_msg_id",
"type",
"text",
"user",
"ts",
"team",
"channel",
"event_ts",
"channel_type",
"is_moved",
"actor_id",
"inviter"
})
public class EventItem {

	@JsonProperty("type")
	private String type;
	@JsonProperty("event_ts")
	private String eventTs;
	private EventItemChannel channel;
	@JsonProperty("user")
	private String user;
	@JsonProperty("team")
	private String team;
	private String channelId;
	@JsonProperty("client_msg_id")
	private String clientMsgId;
	@JsonProperty("text")
	private String text;
	@JsonProperty("ts")
	private String ts;
	@JsonProperty("channel_type")
	private String channelType;
	@JsonProperty("inviter")
	private String inviter;
	@JsonProperty("is_moved")
	public Integer isMoved;
	@JsonProperty("actor_id")
	public String actorId;

	public EventItemChannel getChannel() {
		return channel;
	}
	public String getChannelId() {
		return channelId;
	}
	@JsonSetter("channel")
	public void setChannelInternal(JsonNode channelInternal) {
		if(channelInternal != null) {
			if(channelInternal.isTextual()) {
				channelId = channelInternal.asText();
			} else if (channelInternal.isObject()) {
				String id = channelInternal.get("id").asText();
				Boolean isChannel = channelInternal.get("is_channel").asBoolean();
				String name = channelInternal.get("name").asText();
				String nameNormalized = channelInternal.get("name_normalized").asText();
				Integer created = channelInternal.get("created").intValue();
				if(!type.equals("channel_rename")) {
					String creator = channelInternal.get("creator").asText();
					Boolean isShared = channelInternal.get("is_shared").asBoolean();
					Boolean isOrgShared = channelInternal.get("is_org_shared").asBoolean();
					
					channel = new EventItemChannel(id, isChannel, name, nameNormalized, created, creator, isShared, isOrgShared);
				} else {
					channel = new EventItemChannel(id, isChannel, name, nameNormalized, created);
				}
			}
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}

	public String getEventTs() {
		return eventTs;
	}
	public void setEventTs(String eventTs) {
		this.eventTs = eventTs;
	}

	public String getClientMsgId() {
		return clientMsgId;
	}
	public void setClientMsgId(String clientMsgId) {
		this.clientMsgId = clientMsgId;
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

	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getInviter() {
		return inviter;
	}
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
	
	public Integer getIsMoved() {
		return isMoved;
	}
	public void setIsMoved(Integer isMoved) {
		this.isMoved = isMoved;
	}
	
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
}