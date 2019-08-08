package com.welcome.bot.slack.api.model.interactionpayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"team",
"user",
"api_app_id",
"token",
"container",
"trigger_id",
"channel",
"message",
"response_url",
"actions"
})
public class InteractionPayload {

	@JsonProperty("type")
	public String type;
	@JsonProperty("team")
	public Team team;
	@JsonProperty("user")
	public User user;
	@JsonProperty("api_app_id")
	public String apiAppId;
	@JsonProperty("token")
	public String token;
	@JsonProperty("container")
	public Container container;
	@JsonProperty("trigger_id")
	public String triggerId;
	@JsonProperty("channel")
	public Channel channel;
	@JsonProperty("message")
	public Message message;
	@JsonProperty("response_url")
	public String responseUrl;
	@JsonProperty("actions")
	public List<Action> actions = null;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getApiAppId() {
		return apiAppId;
	}
	public void setApiAppId(String apiAppId) {
		this.apiAppId = apiAppId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Container getContainer() {
		return container;
	}
	public void setContainer(Container container) {
		this.container = container;
	}
	public String getTriggerId() {
		return triggerId;
	}
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public String getResponseUrl() {
		return responseUrl;
	}
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}
	public List<Action> getActions() {
		return actions;
	}
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
}