package com.welcome.bot.slack.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.welcome.bot.slack.api.model.messagepayload.MessagePayload;
import com.welcome.bot.slack.api.model.messagepayload.PayloadAttachment;
import com.welcome.bot.slack.api.model.messagepayload.PayloadBlock;
import com.welcome.bot.slack.api.model.messagepayload.PayloadBlockText;
import com.welcome.bot.slack.api.model.messagepayload.PayloadElement;
import com.welcome.bot.slack.api.model.messagepayload.PayloadElementText;

public class PayloadGenerator {

	DateOperator dateOperator = new DateOperator();

	public PayloadGenerator() {}

	public MessagePayload getMessagePayload(String channel, String text) {
		return generateMessagePayload(channel, text, false);
	}

	public MessagePayload getMessagePayload(String channel, String text, boolean isSmallImage) {
		return generateMessagePayload(channel, text, isSmallImage);
	}

	public MessagePayload getMessagePollPayload(String channel, String text, HashMap<Integer,String> choices, String pollID) {
		return generateMessagePollPayload(channel, text, choices, pollID);
	}

	public MessagePayload getPrivateMessagePayload(String channel, String text, String user) {
		MessagePayload messagePayload = generateMessagePayload(channel, text, false);
		return generatePrivateMessagePayload(messagePayload, user);
	}

	public MessagePayload getPrivateMessagePayload(String channel, String text, String user, boolean isSmallImage) {
		MessagePayload messagePayload = generateMessagePayload(channel, text, isSmallImage);
		return generatePrivateMessagePayload(messagePayload, user);
	}

	public MessagePayload getSchedulePayload(String channel, String text, Date postAt) {
		MessagePayload messagePayload = generateMessagePayload(channel, text, false);
		return generateSchedulePayload(messagePayload, postAt);
	}

	public List<MessagePayload> getScheduleIntervalPayload(String channel, String text, Date postAt){
		return generateScheduleIntervalPayload(channel, text, postAt);
	}

	public MessagePayload getScheduleDeletePayload(String channel, String scheduleID) {
		return generateScheduleDeletePayload(channel,scheduleID);
	}

	public MessagePayload getMessageUpdatePayload(String channel, String newText, String messageTimestamp) {
		return generateMessageUpdatePayload(channel, newText, messageTimestamp);
	}

	public MessagePayload getMessageDeletePayload(String channel, String messageTimestamp) {
		return generateMessageDeletePayload(channel, messageTimestamp);
	}

	private MessagePayload generateMessagePayload(String channel, String text, boolean isSmallImage) {
		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock block = new PayloadBlock();
		PayloadBlock blockTwo = new PayloadBlock();
		PayloadBlockText blockText = new PayloadBlockText();
		List<PayloadBlock> blocks = new ArrayList<>();

		boolean imageExists = false;
		
		if(text.contains("<") && text.contains(">")) {
			text = convertLink(text);
		}

		if(text.contains("[img]") && text.contains("[/img]")) {
			imageExists = true;
			String[] imageData = extractImageData(text);
			text = extractTextWithoutImage(text);
			if (imageData != null) {
				if(isSmallImage) {
					PayloadElement imageElement = new PayloadElement();
					imageElement.setType("image");
					imageElement.setImage_url(imageData[0]);
					imageElement.setAlt_text(imageData[1]);
					block.setAccesosory(imageElement);
				} else {
					blockTwo.setType("image");
					blockTwo.setImage_url(imageData[0]);
					blockTwo.setAlt_text(imageData[1]);
				}
			}
		}

		blockText.setType("mrkdwn");
		blockText.setText(text);

		block.setType("section");
		block.setText(blockText);

		blocks.add(block);
		if(imageExists && blockTwo != null && !isSmallImage) {
			blocks.add(blockTwo);
		}

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

	private MessagePayload generateMessagePollPayload(String channel, String text, HashMap<Integer,String> choices, String pollID) {
		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock pollMessage = new PayloadBlock();
		PayloadBlockText pollMessageText = new PayloadBlockText();

		List<PayloadBlock> blocks = new ArrayList<>();
		List<PayloadAttachment> attachments = new ArrayList<>();

		PayloadBlock choicesBlock = new PayloadBlock();
		List<PayloadElement> choiceElements = new ArrayList<>();

		pollMessageText.setType("mrkdwn");
		pollMessageText.setText(text);
		pollMessage.setType("section");
		pollMessage.setText(pollMessageText);
		blocks.add(pollMessage);

		for(int i=1;i<=choices.size();i++) {
			String id = String.valueOf(i);
			String choice = choices.get(i);

			PayloadElement oneChoiceElement = new PayloadElement();
			PayloadElementText oneChoiceElementText = new PayloadElementText();

			oneChoiceElementText.setType("plain_text");
			oneChoiceElementText.setText(choice);

			oneChoiceElement.setType("button");
			oneChoiceElement.setText(oneChoiceElementText);
			oneChoiceElement.setValue(choice);
			oneChoiceElement.setAction_id(id);

			choiceElements.add(oneChoiceElement);
		}

		choicesBlock.setType("actions");
		choicesBlock.setBlock_id(pollID);
		choicesBlock.setElement(choiceElements);

		blocks.add(choicesBlock);

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

	private MessagePayload generatePrivateMessagePayload(MessagePayload payload, String user) {
		payload.setUser(user);
		return payload;
	}

	private MessagePayload generateSchedulePayload(MessagePayload payload, Date postAt) {
		payload.setPostAt(dateOperator.convertToEpoch(postAt));
		return payload;
	}

	private List<MessagePayload> generateScheduleIntervalPayload(String channel, String text, Date postAt){
		List<MessagePayload> intervalPayload = new ArrayList<>();
		List<Date> intervalDates = dateOperator.generateRepeatTimes(postAt);
		for(Date date : intervalDates) {
			MessagePayload messagePayload = generateMessagePayload(channel, text, false);
			MessagePayload payload = new MessagePayload();
			payload = generateSchedulePayload(messagePayload, date);
			intervalPayload.add(payload);
		}
		return intervalPayload;	
	}

	private MessagePayload generateScheduleDeletePayload(String channel, String scheduleID) {
		MessagePayload payload = new MessagePayload();

		if(channel == null || channel.isEmpty()) {
			channel = "#general";
		}

		payload.setChannel(channel);
		payload.setScheduledMessageId(scheduleID);

		return payload;
	}

	private MessagePayload generateMessageUpdatePayload(String channel, String newText, String messageTimestamp) {
		MessagePayload payload = new MessagePayload();
		payload.setChannel(channel);
		payload.setText(newText);
		payload.setTs(messageTimestamp);
		List<PayloadAttachment> attachments = new ArrayList<>();
		attachments.add(null);
		payload.setAttachments(attachments);
		return payload;
	}

	private MessagePayload generateMessageDeletePayload(String channel, String messageTimestamp) {
		MessagePayload payload = new MessagePayload();
		payload.setChannel(channel);
		payload.setTs(messageTimestamp);
		return payload;
	}

	private String[] extractImageData(String text) {
		String[] imageData;
		if(text.contains("[img]") && text.contains("[/img]")) {
			String extractImage = text.substring(text.indexOf("[img]")+5, text.indexOf("[/img]"));
			String[] extractedImageData = extractImage.split("\\|");
			boolean isUrlOk = checkUrl(extractedImageData[0]);
			if(isUrlOk) {
				if(extractedImageData.length == 2) {
					imageData = extractedImageData;
					return imageData;
				}
			}	
		}
		return null;
	}

	private String extractTextWithoutImage(String text) {
		String extractedText;
		String extractedTextPartOne = text.substring(0, text.indexOf("[img]"));
		String extractedTextPartTwo = text.substring(text.indexOf("[/img]")+6);
		extractedText = extractedTextPartOne + " " + extractedTextPartTwo;
		return extractedText;
	}
	
	private String convertLink(String text) {
		String formatedText = "";
		if(text.contains("<") && text.contains(">")) {
			String extractUrl = text.substring(text.indexOf("<")+1, text.indexOf(">"));
			String[] extractedUrlData = extractUrl.split("\\|");
			String link = extractedUrlData[0];
			String linkText = extractedUrlData[1];
			boolean isLinkOk = checkUrl(link);
			if(isLinkOk) {
				if(!link.contains("http://") && !link.contains("https://")) {
					link = "http://"+link;
				}
			} else {
				return text;
			}
			String formatedLink = "<"+link+"|"+linkText+">"; 
			String textPartOne = text.substring(0, text.indexOf("<")).trim();
			String textPartTwo = text.substring(text.indexOf(">")+1).trim();
			formatedText = textPartOne+" "+formatedLink+" "+textPartTwo;
			return formatedText;
		} else {
			return text;
		}
	}

	private boolean checkUrl(String url) {
		if(url.startsWith("www.") || url.startsWith("http://") || url.startsWith("http://www.") || url.startsWith("https://") || url.startsWith("https://www.")) {
			return true;
		}
		return false;
	}
}