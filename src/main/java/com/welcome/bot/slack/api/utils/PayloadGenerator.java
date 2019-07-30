package com.welcome.bot.slack.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.welcome.bot.slack.api.model.messagepayloadmodel.MessagePayload;
import com.welcome.bot.slack.api.model.messagepayloadmodel.PayloadAttachment;
import com.welcome.bot.slack.api.model.messagepayloadmodel.PayloadBlock;
import com.welcome.bot.slack.api.model.messagepayloadmodel.PayloadBlockText;
import com.welcome.bot.slack.api.model.messagepayloadmodel.PayloadElement;
import com.welcome.bot.slack.api.model.messagepayloadmodel.PayloadElementText;

public class PayloadGenerator {
	
	DateOperator dateUtil = new DateOperator();

	// Constructor
	public PayloadGenerator() {}
	
	public MessagePayload getStyledMessagePayload(String channel, String text) {
		return generateStyledMessagePayload(channel, text);
	}
	
	public MessagePayload getStyledMessageWithButtonPayload(String channel, String text) {
		return generateStyledMessageWithButtonPayload(channel, text);
	}
	
	public MessagePayload getStyledPrivatePayload(String channel, String text, String user) {
		MessagePayload messagePayload = generateStyledMessagePayload(channel, text);
		return generateStyledPrivatePayload(messagePayload, user);
	}
	
	public MessagePayload getStyledSchedulePayload(String channel, String text, Date postAt) {
		MessagePayload messagePayload = generateStyledMessagePayload(channel, text);
		return generateStyledSchedulePayload(messagePayload, postAt);
	}
	
	public MessagePayload getStyledScheduleDeletePayload(String channel, String messageID) {
		return generateStyledScheduleDeletePayload(channel,messageID);
	}
	
	public MessagePayload getStyledReminderPayload(String text, String user, Date remindAt) {
		return generateStyledReminderPayload(text, user, remindAt);
	}
	
	public MessagePayload getStyledReminderDeletePayload(String reminderID) {
		return generateStyledReminderDeletePayload(reminderID);
	}
	
	private MessagePayload generateStyledMessagePayload(String channel, String text) {
		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock block = new PayloadBlock();
		PayloadBlockText blockText = new PayloadBlockText();
		
		blockText.setType("mrkdwn");
		// blockText.setText(text);
		blockText.setText("Hi There and welcome to NSoft universe :wave:\n \nGreat to see you here! This is TEST, and looks like it works. For more information about benefits, flexible work hours, documentation, etc... use available Slack commands.\n\nThat's all for start. Bye :wave:");
		
		block.setType("section");
		block.setText(blockText);
		
		List<PayloadBlock> blocks = new ArrayList<>();
		blocks.add(block);
		
		attachment.setColor("#3AA3E3");
		attachment.setBlocks(blocks);
		
		List<PayloadAttachment> attachments = new ArrayList<>();
		attachments.add(attachment);
		
		payload.setChannel(channel);
		payload.setAttachments(attachments);
		
		return payload;
	}

	private MessagePayload generateStyledMessageWithButtonPayload(String channel, String text) {

		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock block = new PayloadBlock();
		PayloadBlock block_two = new PayloadBlock();
		PayloadBlockText blockText = new PayloadBlockText();
		PayloadElement element = new PayloadElement();
		PayloadElementText elementText = new PayloadElementText();

		elementText.setType("plain_text");
		elementText.setText("ALL COMMANDS");
		elementText.setEmoji(true);

		element.setType("button");;
		element.setText(elementText);
		element.setValue("btn_click_test");

		List<PayloadElement> elements = new ArrayList<>();
		elements.add(element);

		block_two.setType("actions");
		block_two.setElement(elements);

		blockText.setType("mrkdwn");
		//blockText.setText(text);
		blockText.setText("Hi There and welcome to NSoft universe :wave:\n \nGreat to see you here! This is TEST, and looks like it works. For more information about benefits, flexible work hours, documentation, etc... use available Slack commands. \n\nYou can get the list of available commands by clicking the button below\n\nThat's all for start. Bye :wave:");

		block.setType("section");
		block.setText(blockText);

		List<PayloadBlock> blocks = new ArrayList<>();
		blocks.add(block);
		blocks.add(block_two);

		attachment.setColor("#3AA3E3");
		attachment.setBlocks(blocks);

		List<PayloadAttachment> attachments = new ArrayList<>();
		attachments.add(attachment);

		payload.setChannel(channel);
		payload.setAttachments(attachments);

		return payload;
	}
	
	private MessagePayload generateStyledPrivatePayload(MessagePayload payload, String user) {
		payload.setUser(user);
		return payload;
	}
	
	private MessagePayload generateStyledSchedulePayload(MessagePayload payload, Date postAt) {
		payload.setPostAt(dateUtil.convertToEpoch(postAt));
		return payload;
	}
	
	private MessagePayload generateStyledScheduleDeletePayload(String channel, String messageID) {
		MessagePayload payload = new MessagePayload();
		
		payload.setChannel(channel);
		payload.setScheduledMessageId(messageID);
		
		return payload;
	}
	
	private MessagePayload generateStyledReminderPayload(String text, String user, Date remindAt) {
		MessagePayload payload = new MessagePayload();
		
		payload.setText(text);
		payload.setTime(dateUtil.convertToReminder(remindAt));
				
		if(user != null) {
			payload.setUser(user);
		}
		
		return payload;
	}
	
	private MessagePayload generateStyledReminderDeletePayload(String reminderID) {
		MessagePayload payload = new MessagePayload();
		
		payload.setReminder(reminderID);
		
		return payload;
	}
}