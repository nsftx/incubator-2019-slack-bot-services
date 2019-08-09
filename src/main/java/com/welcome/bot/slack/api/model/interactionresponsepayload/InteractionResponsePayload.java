package com.welcome.bot.slack.api.model.interactionresponsepayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"text",
"response_type",
"replace_original",
"delete_original"
})
public class InteractionResponsePayload {
	
	@JsonProperty("text")
	public String text;
	
	@JsonProperty("response_type")
	public String response_type;
	
	@JsonProperty("replace_original")
	public String replaceOriginal;

	@JsonProperty("delete_original")
	public String deleteOriginal;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getResponse_type() {
		return response_type;
	}

	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}

	public String getReplaceOriginal() {
		return replaceOriginal;
	}

	public void setReplaceOriginal(String replaceOriginal) {
		this.replaceOriginal = replaceOriginal;
	}

	public String getDeleteOriginal() {
		return deleteOriginal;
	}

	public void setDeleteOriginal(String deleteOriginal) {
		this.deleteOriginal = deleteOriginal;
	}
	
	
}
