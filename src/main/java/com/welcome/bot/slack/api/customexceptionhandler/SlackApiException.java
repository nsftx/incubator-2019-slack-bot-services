package com.welcome.bot.slack.api.customexceptionhandler;

@SuppressWarnings("serial")
public class SlackApiException extends Exception {

	public SlackApiException(String errorMessage) {
		super(errorMessage);
	}

	public SlackApiException(String errorMessage, Throwable error) {
		super(errorMessage,error);
	}
}