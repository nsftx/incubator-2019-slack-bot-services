package slack.api.model.eventpayloadmodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
"inviter"
})
public class EventItem {

	// COMMON FIELDS FOR ALL EVENTS
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("user")
	private String user;
	
	@JsonProperty("team")
	private String team;
	
	@JsonProperty("channel")
	private String channel;
	
	@JsonProperty("event_ts")
	private String eventTs;
	
	// ADDITIONAL FIELDS FOR "APP_MENTION" EVENT
	@JsonProperty("client_msg_id")
	private String clientMsgId;
	
	@JsonProperty("text")
	private String text;
	
	@JsonProperty("ts")
	private String ts;

	// ADDITIONAL FIELDS FOR "MEMBER_JOINED_CHANNEL" EVENT
	@JsonProperty("channel_type")
	private String channelType;
	
	@JsonProperty("inviter")
	private String inviter;
	
	// GETTERS & SETTERS
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

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
}