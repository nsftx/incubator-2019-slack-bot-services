package com.welcome.bot.slack.api.model.interactionpayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"id",
	"blocks",
	"color",
	"fallback"
})
public class Attachment {

	@JsonProperty("id")
	private Integer id;
	@JsonProperty("blocks")
	private List<Block> blocks = null;
	@JsonProperty("color")
	private String color;
	@JsonProperty("fallback")
	private String fallback;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public List<Block> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String getFallback() {
		return fallback;
	}
	public void setFallback(String fallback) {
		this.fallback = fallback;
	}
}