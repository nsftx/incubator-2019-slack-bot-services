package com.welcome.bot.slack.api.model.messagepayload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"type",
	"text",
	"elements",
	"image_url",
	"alt_text",
	"accessory",
	"block_id"
})
public class PayloadBlock {

	@JsonProperty("type")
	private String type;
	@JsonProperty("text")
	private PayloadBlockText text;
	@JsonProperty("elements")
	private List<PayloadElement> element = null;
	@JsonProperty("image_url")
	private String image_url;
	@JsonProperty("alt_text")
	private String alt_text;
	@JsonProperty("accessory")
	private PayloadElement accesosory;
	@JsonProperty("block_id")
	private String block_id;

	public PayloadBlock() {}

	public PayloadElement getAccesosory() {
		return accesosory;
	}
	public void setAccesosory(PayloadElement accesosory) {
		this.accesosory = accesosory;
	}

	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getAlt_text() {
		return alt_text;
	}
	public void setAlt_text(String alt_text) {
		this.alt_text = alt_text;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public PayloadBlockText getText() {
		return text;
	}
	public void setText(PayloadBlockText text) {
		this.text = text;
	}

	public List<PayloadElement> getElement() {
		return element;
	}
	public void setElement(List<PayloadElement> element) {
		this.element = element;
	}

	public String getBlock_id() {
		return block_id;
	}
	public void setBlock_id(String block_id) {
		this.block_id = block_id;
	}
}