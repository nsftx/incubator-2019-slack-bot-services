package slack.api;

import java.util.Date;
import java.util.HashMap;

public interface SlackClientApi {

	// Send message to slack
	boolean sendMessage(String channel, String text);
	
	// Send private message to user on slack
	boolean sendPrivateMessage(String channel, String text, String user);
	
	// Send schedule to slack
	String createSchedule(String channel, String text, Date postAt);
	
	// Delete schedule from slack
	void deleteSchedule(String channel, String messageID);
	
	// Send reminder to slack
	String createReminder(String text, String user /*user-optional, one more parameter for time*/);
	
	// Delete reminder from slack
	void deleteReminder(String reminderID);
	
	// Get list of all channels
	HashMap<String,String> getChannelsList();
}