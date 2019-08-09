package com.welcome.bot.slack.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.welcome.bot.slack.api.model.interactionpayload.Channel;

public interface SlackClientApi {
	
	/**
	 * Method used to send public messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	HashMap<String,String> sendMessage(String channel, String text);
	
	/**
	 * Method used to send private messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send,
	 * @param user - User to which You want to send private message.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	HashMap<String,String> sendMessage(String channel, String text, String user);
	
	/**
	 * Method used to send public messages to Slack, but with extra argument used to set type of image if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send,
	 * @param sendSmallImage - boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	//HashMap<String,String> sendMessage(String channel, String text, boolean sendSmallImage);
	//check should I overload method because of boolean argument, or set it in first method, and user passes false by default
	
	/**
	 * Method used to send private message to Slack, but with extra argument used to set type of image if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send,
	 * @param user - User to which You want to send private message,
	 * @param sendSmallImage - boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	//HashMap<String,String> sendMessage(String channel, String text, String user, boolean sendSmallImage);
	//check should I overload method because of boolean argument, or set it in first method, and user passes false by default
	
	/**
	 * Method used to send POLL messages to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send,
	 * @param voteOptions - List of values that will be presented to users as options for voting.
	 * @return HashMap<'String,String'> status: 1st pair-> status:ok/error,2nd pair-> message:"success/error details".
	 */
	HashMap<String,String> sendMessagePoll(String channel, String text, List<String> voteOptions);
	
	/**
	 * Method used to create schedules on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message which You want to schedule,
	 * @param postAt - Date and Time at which message will be posted.
	 * @return String messageID: If message was scheduled successfully, method will return ID of the scheduled message. Else, it will return null, which means message wasn't scheduled.
	 */
	String createSchedule(String channel, String text, Date postAt);
	
	/**
	 * Method used to create recurring schedules on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message which You want to schedule,
	 * @param postAt - Date and Time at which message will be posted for the first time.
	 * @param repeatInterval - Text indicating the interval of recurring schedule. Can be DAILY | WEEKLY | MONTHLY
	 * @return String messageID: If messages were scheduled successfully, method will return IDs of all scheduled messages as one String. Else, it will return null, which means message wasn't scheduled.
	 */
	String createSchedule(String channel, String text, Date postAt, String repeatInterval);
	
	/**
	 * Method used to delete schedules from Slack.
	 * @param messageID	- ID of schedule user wants to delete,
	 * @param channel -	Channel where message was scheduled,
	 * @return boolean indicating if message was deleted successfully (TRUE-deleted,FALSE-not deleted).
	 */
	boolean deleteSchedule(String messageID, String channel);
	
	/**
	 * Method used to get list of all channels that user can send messages to.
	 * @return List of Channel objects with name and id of each channel
	 */
	List<Channel> getChannelsList();
	
	/**
	 * Method used to get link to Normal Message Preview in Block Kit Builder
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send.
	 * @return String URL link that opens preview.
	 */
	String getMessagePreviewLink(String channel, String text);
	
	/**
	 * Method used to get link to Poll Message Preview in Block Kit Builder
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message which You want to send.
	 * @param voteOptions - List of values that will be presented to users as options for voting.
	 * @return String URL link that opens preview.
	 */
	String getMessagePollPreviewLink(String channel, String text, List<String> voteOptions);
}