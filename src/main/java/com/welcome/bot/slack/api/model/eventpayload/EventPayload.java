package com.welcome.bot.slack.api.model.eventpayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"token",
"team_id",
"api_app_id",
"event",
"challenge",
"type",
"event_id",
"event_time",
"authed_users"
})

public class EventPayload {
	
	// COMMON FIELDS
	@JsonProperty("token")
	private String token;
	
	@JsonProperty("type")
	private String type;

	// ADDITIONAL FIELDS FOR EVENT_SUBSCRIPTIONS
	@JsonProperty("team_id")
	private String teamId = "";
	
	@JsonProperty("api_app_id")
	private String apiAppId = "";
	
	@JsonProperty("event")
	private EventItem eventItem = null;
	
	@JsonProperty("event_id")
	private String eventId = "";
	
	@JsonProperty("event_time")
	private Integer eventTime = 0;
	
	@JsonProperty("authed_users")
	private List<String> authedUsers = null;
	
	// ADDITIONAL FIELDS FOR URL_VERIFICATION
	@JsonProperty("challenge")
	private String challenge = "";

	// GETTERS & SETTERS
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getApiAppId() {
		return apiAppId;
	}
	public void setApiAppId(String apiAppId) {
		this.apiAppId = apiAppId;
	}

	public EventItem getEventItem() {
		return eventItem;
	}
	public void setEventItem(EventItem eventItem) {
		this.eventItem = eventItem;
	}

	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Integer getEventTime() {
		return eventTime;
	}
	public void setEventTime(Integer eventTime) {
		this.eventTime = eventTime;
	}

	public List<String> getAuthedUsers() {
		return authedUsers;
	}
	public void setAuthedUsers(List<String> authedUsers) {
		this.authedUsers = authedUsers;
	}

	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
}