package com.welcome.bot.slack.api.customexceptionhandler;

@SuppressWarnings("serial")
public class MessageSendingException extends SlackApiException {

	public MessageSendingException(String errorMessage) {
		super(errorMessage);
	}

	public MessageSendingException(String errorMessage, Throwable error) {
		super(errorMessage,error);
	}
}