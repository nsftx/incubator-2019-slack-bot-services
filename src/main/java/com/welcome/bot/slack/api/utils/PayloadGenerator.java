package com.welcome.bot.slack.api.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.welcome.bot.slack.api.model.interactionresponsepayload.InteractionResponsePayload;
import com.welcome.bot.slack.api.model.messagepayload.MessagePayload;
import com.welcome.bot.slack.api.model.messagepayload.PayloadAttachment;
import com.welcome.bot.slack.api.model.messagepayload.PayloadBlock;
import com.welcome.bot.slack.api.model.messagepayload.PayloadBlockText;
import com.welcome.bot.slack.api.model.messagepayload.PayloadElement;
import com.welcome.bot.slack.api.model.messagepayload.PayloadElementText;

public class PayloadGenerator {

	DateOperator dateUtil = new DateOperator();

	// Constructor
	public PayloadGenerator() {}

	public MessagePayload getMessagePayload(String channel, String text) {
		return generateMessagePayload(channel, text);
	}

	public MessagePayload getMessagePollPayload(String channel, String text, List<String> voteOptions) {
		return generateMessagePollPayload(channel, text, voteOptions);
	}

	public MessagePayload getPrivateMessagePayload(String channel, String text, String user) {
		MessagePayload messagePayload = generateMessagePayload(channel, text);
		return generatePrivateMessagePayload(messagePayload, user);
	}

	public MessagePayload getSchedulePayload(String channel, String text, Date postAt) {
		MessagePayload messagePayload = generateMessagePayload(channel, text);
		return generateSchedulePayload(messagePayload, postAt);
	}
	
	public List<MessagePayload> getScheduleIntervalPayload(String channel, String text, Date postAt, String repeatInterval){
		return generateScheduleIntervalPayload(channel, text, postAt, repeatInterval);
	}

	public MessagePayload getScheduleDeletePayload(String channel, String messageID) {
		return generateScheduleDeletePayload(channel,messageID);
	}
	
	public InteractionResponsePayload getInteractionResponsePayload(String channel, String text) {
		return generateInteractionResponsePayload(channel, text);
	}

	private MessagePayload generateMessagePayload(String channel, String text) {
		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock block = new PayloadBlock();
		PayloadBlock blockTwo = new PayloadBlock();
		PayloadBlockText blockText = new PayloadBlockText();
		List<PayloadBlock> blocks = new ArrayList<>();
		
		 //TODO test - delete later (for image testing)
		text = "Hi There and welcome to NSoft universe :wave:\n \nGreat to see you here!{-[https://api.slack.com/img/blocks/bkb_template_images/beagle.png, image alt text]-} This is TEST, and looks like it works.";

		boolean sendSmallImage = false; // this argument will be passed into method, not defined here.
		
		String[] imageData = extractImageData(text);
		if (imageData != null) {
			if(sendSmallImage) {
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

		blockText.setType("mrkdwn");
		//blockText.setText(text);
		blockText.setText("Hi There and welcome to NSoft universe :wave:\n \nGreat to see you here! This is TEST, and looks like it works. For more information about benefits, flexible work hours, documentation, etc... use available Slack commands.\n\nThat's all for start. Bye :wave:");

		block.setType("section");
		block.setText(blockText);

		blocks.add(block);
		if(imageData != null && blockTwo != null) {
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

	private MessagePayload generateMessagePollPayload(String channel, String text, List<String> voteOptions) {

		MessagePayload payload = new MessagePayload();
		PayloadAttachment attachment = new PayloadAttachment();
		PayloadBlock voteMessage = new PayloadBlock();
		PayloadBlockText voteMessageText = new PayloadBlockText();
		
		List<PayloadBlock> blocks = new ArrayList<>();
		List<PayloadAttachment> attachments = new ArrayList<>();
		
		PayloadBlock voteOptionsBlock = new PayloadBlock();
		List<PayloadElement> voteOptionElements = new ArrayList<>();
		
		voteMessageText.setType("mrkdwn");
		voteMessageText.setText(text);
		voteMessage.setType("section");
		voteMessage.setText(voteMessageText);
		blocks.add(voteMessage);
		
		for(String voteOption : voteOptions) {
			PayloadElement oneVoteOptionElement = new PayloadElement();
			PayloadElementText oneVoteOptionElementText = new PayloadElementText();
			
			oneVoteOptionElementText.setType("plain_text");
			oneVoteOptionElementText.setText(voteOption);
			
			oneVoteOptionElement.setType("button");
			oneVoteOptionElement.setText(oneVoteOptionElementText);
			oneVoteOptionElement.setValue(voteOption);
			
			voteOptionElements.add(oneVoteOptionElement);
		}
		
		voteOptionsBlock.setType("actions");
		//voteOptionsBlock.setBlockId("s"); // BLOCK ID to differentiate one voting block from another
		voteOptionsBlock.setElement(voteOptionElements);
		
		blocks.add(voteOptionsBlock);
		
		
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
		payload.setPostAt(dateUtil.convertToEpoch(postAt));
		return payload;
	}
	
	private List<MessagePayload> generateScheduleIntervalPayload(String channel, String text, Date postAt, String repeatInterval){
		List<MessagePayload> intervalPayload = new ArrayList<>();
		List<Date> intervalDates = dateUtil.generateRepeatTimes(postAt, repeatInterval);
		for(Date date : intervalDates) {
			MessagePayload messagePayload = generateMessagePayload(channel, text);
			MessagePayload payload = new MessagePayload();
			payload = generateSchedulePayload(messagePayload, date);
			intervalPayload.add(payload);
		}
		return intervalPayload;	
	}

	private MessagePayload generateScheduleDeletePayload(String channel, String messageID) {
		MessagePayload payload = new MessagePayload();

		if(channel == null || channel.isEmpty()) {
			channel = "#general";
		}

		payload.setChannel(channel);
		payload.setScheduledMessageId(messageID);

		return payload;
	}

	// for response to interaction
	private InteractionResponsePayload generateInteractionResponsePayload(String channel, String text) {
		InteractionResponsePayload payload = new InteractionResponsePayload();
		
		payload.setText(text);
		payload.setResponse_type("ephemeral");
		
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