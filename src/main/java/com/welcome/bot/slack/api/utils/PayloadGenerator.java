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

	public MessagePayload getStyledMessagePollPayload(String channel, String text, List<String> voteOptions) {
		return generateStyledMessagePollPayload(channel, text, voteOptions);
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

	public MessagePayload getStyledReminderPayload(String text, Date postAt, String user) {
		return generateStyledReminderPayload(text, postAt, user);
	}

	public MessagePayload getStyledReminderDeletePayload(String reminderID) {
		return generateStyledReminderDeletePayload(reminderID);
	}

	private MessagePayload generateStyledMessagePayload(String channel, String text) {
		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock block = new PayloadBlock();
		PayloadBlockText blockText = new PayloadBlockText();
		List<PayloadBlock> blocks = new ArrayList<>();
		
		// for image testing
		//text = "Hi There and welcome to NSoft universe :wave:\\n \\nGreat to see you here!{-[https://api.slack.com/img/blocks/bkb_template_images/beagle.png, image alt text]-} This is TEST, and looks like it works.";

		String[] imageData = extractImageData(text);
		if (imageData != null) {
			PayloadBlock blockTwo = new PayloadBlock();
			blockTwo.setType("image");
			blockTwo.setImage_url(imageData[0]);
			blockTwo.setAlt_text(imageData[1]);
			blocks.add(blockTwo);
		}

		blockText.setType("mrkdwn");
		//blockText.setText(text);
		blockText.setText("Hi There and welcome to NSoft universe :wave:\n \nGreat to see you here! This is TEST, and looks like it works. For more information about benefits, flexible work hours, documentation, etc... use available Slack commands.\n\nThat's all for start. Bye :wave:");

		block.setType("section");
		block.setText(blockText);

		blocks.add(block);

		attachment.setColor("#3AA3E3");
		attachment.setBlocks(blocks);

		List<PayloadAttachment> attachments = new ArrayList<>();
		attachments.add(attachment);

		if(channel == null || channel.isEmpty()) {
			channel = "#general";
		}

		payload.setChannel(channel);
		payload.setAttachments(attachments);

		return payload;
	}

	private MessagePayload generateStyledMessagePollPayload(String channel, String text, List<String> voteOptions) {

		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock voteMessage = new PayloadBlock();
		PayloadBlockText voteMessageText = new PayloadBlockText();
		
		List<PayloadBlock> blocks = new ArrayList<>();
		List<PayloadAttachment> attachments = new ArrayList<>();
		
		voteMessageText.setType("mrkdwn");
		voteMessageText.setText(text);
		voteMessage.setType("section");
		voteMessage.setText(voteMessageText);
		blocks.add(voteMessage);
		
		for(String voteOption : voteOptions) {
			PayloadBlock voteOptionBlock = new PayloadBlock();
			PayloadBlockText voteOptionText = new PayloadBlockText();
			
			PayloadElement voteOptionBlockElement = new PayloadElement();
			PayloadElementText voteOptionBlockElementText = new PayloadElementText();
			
			voteOptionBlockElementText.setType("plain_text");
			voteOptionBlockElementText.setText("Vote");
			voteOptionBlockElementText.setEmoji(true);

			voteOptionBlockElement.setType("button");;
			voteOptionBlockElement.setText(voteOptionBlockElementText);
			voteOptionBlockElement.setValue(voteOption);

			voteOptionText.setType("mrkdwn");
			voteOptionText.setText(voteOption);

			voteOptionBlock.setType("section");
			voteOptionBlock.setText(voteOptionText);
			voteOptionBlock.setAccesosory(voteOptionBlockElement);
			
			blocks.add(voteOptionBlock);
		}
		attachment.setColor("#3AA3E3");
		attachment.setBlocks(blocks);
		
		attachments.add(attachment);

		if(channel == null || channel.isEmpty()) {
			channel = "#general";
		}

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

		if(channel == null || channel.isEmpty()) {
			channel = "#general";
		}

		payload.setChannel(channel);
		payload.setScheduledMessageId(messageID);

		return payload;
	}

	private MessagePayload generateStyledReminderPayload(String text, Date postAt, String user) {
		MessagePayload payload = new MessagePayload();

		payload.setText(text);
		payload.setTime(dateUtil.convertToReminder(postAt));

		if(user != null) {
			payload.setUser(user);
		}

		return payload;
	}

	private MessagePayload generateStyledReminderDeletePayload(String messageID) {
		MessagePayload payload = new MessagePayload();
		payload.setReminder(messageID);
		return payload;
	}

	private String[] extractImageData(String text) {
		String[] imageData;
		if(text.contains("{-[") && text.contains("]-}")) {
			String extractImage = text.substring(text.indexOf("{-[")+3, text.indexOf("]-}"));
			String[] extractedData = extractImage.split(",");
			boolean isUrlOk = checkImageUrl(extractedData[0]);
			if(isUrlOk) {
				if(extractedData.length == 2) {
					imageData = extractedData;
					return imageData;
				}
			}	
		}
		return null;
	}

	private boolean checkImageUrl(String imageUrl) {
		if(imageUrl.startsWith("www.") || imageUrl.startsWith("http://") || imageUrl.startsWith("http://www.") || imageUrl.startsWith("https://") || imageUrl.startsWith("https://www.")) {
			return true;
		}
		return false;
	}
}