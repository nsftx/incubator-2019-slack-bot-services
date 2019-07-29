package slack.api.model.messagepayloadmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"color",
"blocks"
})
public class PayloadAttachment {

	// Properties
	@JsonProperty("color")
	private String color;
	@JsonProperty("blocks")
	private List<PayloadBlock> blocks = null;
	
	// Constructor
	public PayloadAttachment() {}
	
	// Getters/Setters
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public List<PayloadBlock> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<PayloadBlock> blocks) {
		this.blocks = blocks;
	}
}