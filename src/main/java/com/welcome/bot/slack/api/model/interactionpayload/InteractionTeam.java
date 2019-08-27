package com.welcome.bot.slack.api.model.interactionpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"domain"
})
public class InteractionTeam {

	@JsonProperty("id")
	private String id;
	@JsonProperty("domain")
	private String domain;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}