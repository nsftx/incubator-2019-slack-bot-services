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
	private String type;
	@JsonProperty("team")
	private InteractionTeam team;
	@JsonProperty("user")
	private InteractionUser user;
	@JsonProperty("api_app_id")
	private String apiAppId;
	@JsonProperty("token")
	private String token;
	@JsonProperty("container")
	private InteractionContainer container;
	@JsonProperty("trigger_id")
	private String triggerId;
	@JsonProperty("channel")
	private Channel channel;
	@JsonProperty("message")
	private InteractionMessage message;
	@JsonProperty("response_url")
	private String responseUrl;
	@JsonProperty("actions")
	private List<InteractionAction> actions = null;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public InteractionTeam getTeam() {
		return team;
	}
	public void setTeam(InteractionTeam team) {
		this.team = team;
	}

	public InteractionUser getUser() {
		return user;
	}
	public void setUser(InteractionUser user) {
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

	public InteractionContainer getContainer() {
		return container;
	}
	public void setContainer(InteractionContainer container) {
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

	public InteractionMessage getMessage() {
		return message;
	}
	public void setMessage(InteractionMessage message) {
		this.message = message;
	}

	public String getResponseUrl() {
		return responseUrl;
	}
	public void setResponseUrl(String responseUrl) {
		this.responseUrl = responseUrl;
	}

	public List<InteractionAction> getActions() {
		return actions;
	}
	public void setActions(List<InteractionAction> actions) {
		this.actions = actions;
	}
}