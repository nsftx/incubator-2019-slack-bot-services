package slack.api.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionGenerator {
	
	static final String TOKEN_BOT = "xoxb-692746321984-692302263812-pbCjphAhfxxASnvKYj6HwMOa";
	static final String TOKEN_USER = "xoxp-692746321984-692279219956-706352001428-28053851304311795bd4ccebeece5302";

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
	
	public HttpURLConnection getReminderMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/reminders.add");
		return generateConnection(url, TOKEN_USER);
	}
	
	public HttpURLConnection getDeleteReminderMsgConnection() throws IOException {
		URL url = new URL("https://slack.com/api/reminders.delete");
		return generateConnection(url, TOKEN_USER);
	}
	
	public HttpURLConnection getChannelsListConnection() throws IOException {
		URL url = new URL("https://slack.com/api/conversations.list");
		return generateGETConnection(url, TOKEN_BOT);
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