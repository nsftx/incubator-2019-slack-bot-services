package com.welcome.bot.slack.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;
import com.welcome.bot.slack.api.model.interactionpayload.Channel;

public interface SlackClientApi {

	/**
	 * METHOD to send public message to Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void sendMessage(String channel, String text) throws SlackApiException;

	/**
	 * METHOD to send public message to Slack, with extra argument to define way of displaying image, if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param isSmallImage - boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void sendMessage(String channel, String text, boolean isSmallImage) throws SlackApiException;

	/**
	 * METHOD to send private message to user on Slack.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param user - User to whom message will be sent.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void sendMessage(String channel, String text, String user) throws SlackApiException;

	/**
	 * METHOD to send private message to user on Slack, with extra argument to define way of displaying image, if there is any.
	 * @param channel - Name of channel to which You want to send message,
	 * @param text - Text of the message being send,
	 * @param user - User to whom message will be sent,
	 * @param isSmallImage - Boolean to indicate which type of image to send, true - send small image | false - send large image.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void sendMessage(String channel, String text, String user, boolean isSmallImage) throws SlackApiException;

	/**
	 * METHOD to send POLL message to Slack.
	 * @param channel - Name of channel to which You want to send poll,
	 * @param text - Text of the poll being send,
	 * @param choices - Map of id/value pairs that will be presented to users as options for voting,
	 * @param pollID - Unique ID of poll to create, used to map votes with right poll, and differentiate polls from each other.
	 * @return String messageTimestamp - If poll is sent successfully, method will return time stamp of the poll.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	String sendMessagePoll(String channel, String text, HashMap<Integer,String> choices, UUID pollID) throws SlackApiException;

	/**
	 * METHOD to update specific message/poll in Slack
	 * @param channel - Name of channel where message was posted,
	 * @param newText - New text that will replace previous message text,
	 * @param messageTimestamp - Time stamp of message (time at which message was posted)
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void updateMessage(String channel, String newText, String messageTimestamp) throws SlackApiException;

	/**
	 * METHOD to delete specific message/poll from Slack.
	 * @param channel - Name of channel where message was posted,
	 * @param messageTimestamp - Time stamp of message (time at which message was posted).
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void deleteMessage(String channel, String messageTimestamp) throws SlackApiException;

	/**
	 * METHOD to schedule message on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message being scheduled,
	 * @param postAt - Date and Time at which message will be posted.
	 * @return String scheduleID - If message was scheduled successfully, method will return ID of the scheduled message.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	String createSchedule(String channel, String text, Date postAt) throws SlackApiException;

	/**
	 * METHOD to schedule repeating message on Slack.
	 * @param channel - Name of channel to which You want to schedule message,
	 * @param text - Text of the message being scheduled,
	 * @param postAt - Date and Time at which message will be posted for the first time,
	 * @param repeatInterval - Text indicating the interval of recurring schedule. Can be DAILY | WEEKLY | MONTHLY.
	 * @return String scheduleID - If message was scheduled successfully, method will return all scheduled messages IDs in one String.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	String createSchedule(String channel, String text, Date postAt, String repeatInterval) throws SlackApiException;

	/**
	 * METHOD to delete schedule from Slack.
	 * @param channel -	Channel where message was scheduled,
	 * @param scheduleID - ID of schedule user wants to delete.
	 * @throws SlackApiException - If something is wrong (arguments invalid, sending failed), exception will be thrown.
	 */
	void deleteSchedule(String channel, String scheduleID) throws SlackApiException;

	/**
	 * METHOD to get list of all channels from Slack.
	 * @return List of Channel objects with name and id of each channel.
	 */
	List<Channel> getChannelsList();
}