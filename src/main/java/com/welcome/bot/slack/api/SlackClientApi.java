package com.welcome.bot.slack.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.welcome.bot.slack.api.model.interactionpayload.Channel;

public interface SlackClientApi {
	
	/**
	 * Method used to send public messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send.
	 * @return boolean - Returns TRUE if message is sent successfully. ELSE, it throws exception.
	 */
	boolean sendMessage(String channel, String text);
	
	/**
	 * Method used to send public messages to Slack, but with extra argument used to set type of image if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param isSmallImage - boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @return boolean - Returns TRUE if message is sent successfully. ELSE, it throws exception.
	 */
	boolean sendMessage(String channel, String text, boolean isSmallImage);
	
	/**
	 * Method used to send private messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param user - User to whom message will be sent.
	 * @return boolean - Returns TRUE if message is sent successfully. ELSE, it throws exception.
	 */
	boolean sendMessage(String channel, String text, String user);
	
	/**
	 * Method used to send private message to Slack, but with extra argument used to set type of image if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param user - User to whom message will be sent,
	 * @param isSmallImage - Boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @return boolean - Returns TRUE if message is sent successfully. ELSE, it throws exception.
	 */
	boolean sendMessage(String channel, String text, String user, boolean isSmallImage);
	
	/**
	 * Method used to send POLL messages to Slack.
	 * @param channel - Name of channel to which You want to send poll,
	 * @param text - Text of the poll being send,
	 * @param choices - Map of id/value pairs that will be presented to users as options for voting,
	 * @param pollID - Unique ID of poll to create, used to map votes with right poll, and differentiate polls from each other.
	 * @return String messageTimestamp - If poll is sent successfully, method will return timestamp of the poll. ELSE, it throws exception.
	 */
	String sendMessagePoll(String channel, String text, HashMap<Integer,String> choices, UUID pollID);
	
	/**
	 * Message used to update messages/polls in Slack
	 * @param channel - Name of channel where message was posted,
	 * @param newText - New text that will replace previous message text,
	 * @param messageTimestamp - Timestamp of message (time at which message was posted)
	 * @return boolean - Returns TRUE if message/poll is updated successfully. ELSE, it throws exception.
	 */
	boolean updateMessage(String channel, String newText, String messageTimestamp);
	
	/**
	 * Method used to delete messages/polls from Slack
	 * @param channel - Name of channel where message was posted,
	 * @param messageTimestamp - Timestamp of message (time at which message was posted).
	 * @return boolean - Returns TRUE if message/poll is deleted successfully. ELSE, it throws exception.
	 */
	boolean deleteMessage(String channel, String messageTimestamp);
	
	/**
	 * Method used to create schedules on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message being scheduled,
	 * @param postAt - Date and Time at which message will be posted.
	 * @return String scheduleID - If message was scheduled successfully, method will return ID of the scheduled message. ELSE, it throws exception.
	 */
	String createSchedule(String channel, String text, Date postAt);
	
	/**
	 * Method used to create recurring schedules on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message being scheduled,
	 * @param postAt - Date and Time at which message will be posted for the first time,
	 * @param repeatInterval - Text indicating the interval of recurring schedule. Can be DAILY | WEEKLY | MONTHLY.
	 * @return String scheduleID - If message was scheduled successfully, method will return all scheduled messages IDs in one String. ELSE, it throws exception.
	 */
	String createSchedule(String channel, String text, Date postAt, String repeatInterval);
	
	/**
	 * Method used to delete schedules from Slack.
	 * @param scheduleID - ID of schedule user wants to delete,
	 * @param channel -	Channel where message was scheduled.
	 * @return boolean - Returns TRUE if schedule is deleted successfully. ELSE, it throws exception.
	 */
	boolean deleteSchedule(String scheduleID, String channel);
	
	/**
	 * Method used to get list of all channels that user can send messages to.
	 * @return List - List of Channel objects with name and id of each channel.
	 */
	List<Channel> getChannelsList();
}