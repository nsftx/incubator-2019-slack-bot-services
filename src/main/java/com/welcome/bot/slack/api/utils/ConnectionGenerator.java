package com.welcome.bot.slack.api.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionGenerator {

	// Bot-TestEnv - workspace Auth
//	static final String TOKEN_BOT_1 = "xoxb-692746321984-";
//	static final String TOKEN_BOT_2 = "692302263812-Pauu27Zu8eB9Vl4m8rtyhO55";
//	static final String TOKEN_USER_1 = "xoxp-692746321984-692279219956-";
//	static final String TOKEN_USER_2 = "725376523793-03a717bf62ff4aa55a9e0e393c4d8fca";
	
	// Nsoft-WelcomeBot - workspace Auth
	static final String TOKEN_BOT_1 = "xoxb-727927339459-";
	static final String TOKEN_BOT_2 = "727951826259-TarVbqUpe0D4CWuPJNSyrXgy";
	static final String TOKEN_USER_1 = "xoxp-727927339459-727923134898-";
	static final String TOKEN_USER_2 = "727947654738-e1531a6cde0bee4841778d3c36919cc4";

	public ConnectionGenerator() {}

	public HttpURLConnection getPostMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.postMessage");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getPrivateMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.postEphemeral");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getScheduleMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.scheduleMessage");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getDeleteScheduledMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.deleteScheduledMessage");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getChannelsListConnection() throws IOException {
		URL url = new URL("https://slack.com/api/conversations.list");
		return generateGETConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getSchedulesListConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.scheduledMessages.list");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}
	public HttpURLConnection getMessageUpdateConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.update");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	public HttpURLConnection getMessageDeleteConnection() throws IOException {
		URL url = new URL("https://slack.com/api/chat.delete");
		return generatePOSTConnection(url, TOKEN_BOT_1+TOKEN_BOT_2);
	}

	private HttpURLConnection generatePOSTConnection(URL url, String token) throws IOException {
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