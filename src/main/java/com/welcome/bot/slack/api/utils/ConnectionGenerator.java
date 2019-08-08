package com.welcome.bot.slack.api.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionGenerator {
	
	static final String TOKEN_BOT = "xoxb-692746321984-692302263812-xcMVBuQg4dZvXw5mnobQGZOL";
	static final String TOKEN_USER = "xoxp-692746321984-692279219956-705915627570-61300ce3bdc65dca4571a71a235da14e";

	public ConnectionGenerator() {}
	
	public HttpURLConnection getPostMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.postMessage");
		return generateConnection(url, TOKEN_BOT);
	}
	
	public HttpURLConnection getPrivateMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.postEphemeral");
		return generateConnection(url, TOKEN_BOT);
	}
	
	public HttpURLConnection getScheduleMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.scheduleMessage");
		return generateConnection(url, TOKEN_BOT);
	}
	
	public HttpURLConnection getDeleteScheduledMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.deleteScheduledMessage");
		return generateConnection(url, TOKEN_BOT);
	}
	
	public HttpURLConnection getChannelsListConnection() throws IOException {
		URL url = new URL("https://slack.com/api/conversations.list");
		return generateGETConnection(url, TOKEN_BOT);
	}
	
	public HttpURLConnection getSchedulesListConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.scheduledMessages.list");
		return generateConnection(url, TOKEN_BOT);
	}
	
	private HttpURLConnection generateConnection(URL url, String token) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Authorization", "Bearer " + token);
		connection.setDoOutput(true);
		return connection;
	}
	
	private HttpURLConnection generateGETConnection(URL url, String token) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Authorization", "Bearer " + token);
		return connection;
	}
}