package com.welcome.bot.slack.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface SlackClientApi {
	
	/**
	 * For sending public messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	HashMap<String,String> sendMessage(String channel, String text);
	
	/**
	 * For sending private messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send,
	 * @param user - User to which You want to send private message.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	HashMap<String,String> sendMessage(String channel, String text, String user);
	
	/**
	 * 
	 * @param channel
	 * @param text
	 * @param voteOptions
	 * @return
	 */
	HashMap<String,String> sendMessagePoll(String channel, String text, List<String> voteOptions);
	
	/**
	 * For creating schedules on Slack. You Can create non-recurring and recurring schedules.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message which You want to schedule,
	 * @param postAt - Date and Time at which message will be posted,
	 * @param doRepeat - TRUE if message is recurring, and FALSE if message is non-recurring,
	 * @return String messageID: If message was scheduled successfully, method will return ID of the scheduled message. Else, it will return null, which means message wasn't scheduled.
	 */
	String createSchedule(String channel, String text, Date postAt, boolean doRepeat);
	
	/**
	 * For creating schedules for certain user on Slack. You Can create only recurring schedules for certain user.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message which You want to schedule,
	 * @param postAt - Date and Time at which message will be posted,
	 * @param doRepeat - TRUE if message is recurring, and FALSE if message is non-recurring,
	 * @param user - Set schedule for certain user.
	 * @return String messageID: If message was scheduled successfully, method will return ID of the scheduled message. Else, it will return null, which means message wasn't scheduled.
	 */
	//String createSchedule(String channel, String text, Date postAt, boolean doRepeat, String user);
	/*
	 * DELETE METHOD ABOVE (REMINDERS --- REPEATING SCHEDULES...AND USE JAVA SCHEDULER, INSIDE THE APP, AND SEND NEW SCHEDULES WHEN EXISTING IS SENT, IF REPEAT IS TRUE)
	 */
	
	/**
	 * For deleting schedules from Slack.
	 * @param messageID	- ID of message user wants to delete,
	 * @param channel -	Channel where message was scheduled,
	 * @param wasRepeat	- TRUE if message was recurring, false if not.
	 * @return boolean indicating if message was deleted successfully (TRUE-deleted,FALSE-not deleted).
	 */
	boolean deleteSchedule(String messageID, String channel, boolean wasRepeat);
	
	/**
	 * Get list of all channels that user can send message to.
	 * @return List<'String'> of available channels
	 */
	List<String> getChannelsList();
	
	/**
	 * 
	 * @param channel
	 * @param text
	 * @return
	 */
	String getMessagePreviewLink(String channel, String text);
	
	/**
	 * Get link to Poll Message Preview
	 * @param channel
	 * @param text
	 * @return
	 */
	String getMessagePollPreviewLink(String channel, String text, List<String> voteOptions);
}