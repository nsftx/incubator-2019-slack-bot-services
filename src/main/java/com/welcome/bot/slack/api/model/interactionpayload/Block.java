package com.welcome.bot.slack.api.model.interactionpayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"block_id",
"text",
"elements"
})
public class Block {

	@JsonProperty("type")
	private String type;
	@JsonProperty("block_id")
	private String blockId;
	@JsonProperty("text")
	private Text text;
	@JsonProperty("elements")
	private List<Element> elements = null;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	
	public Text getText() {
		return text;
	}
	public void setText(Text text) {
		this.text = text;
	}
	
	public List<Element> getElements() {
		return elements;
	}
	public void setElements(List<Element> elements) {
		this.elements = elements;
	}
}