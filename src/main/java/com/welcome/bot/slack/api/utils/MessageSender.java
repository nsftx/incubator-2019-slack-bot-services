package com.welcome.bot.slack.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageSender {

	public MessageSender() {}

	public boolean sendMessageAndGetStatus(HttpURLConnection slackConnection, String payload) {
		boolean status = false;
		JSONObject result = sendMessageReadResponse(slackConnection, payload);

		try {
			status = result.getBoolean("ok");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return status;
	}

	public String sendScheduleAndGetMessageId(HttpURLConnection slackConnection, String payload) {
		String messageID = "";
		JSONObject result = sendMessageReadResponse(slackConnection, payload);

		try {
			boolean isOk = result.getBoolean("ok");
			if(isOk) {
				messageID = result.getString("scheduled_message_id");
			} else {
				messageID = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return messageID;
	}

	public String sendReminderAndGetReminderId(HttpURLConnection slackConnection, String payload) {
		String reminderID = "";
		JSONObject result = sendMessageReadResponse(slackConnection, payload);

		try {
			reminderID = result.getJSONObject("reminder").getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return reminderID;
	}
	
	public List<String> sendRequestToGetChannelsList(HttpURLConnection slackConnection) {
		List<String> channelList = new ArrayList<>();
		JSONObject result = getChannelsList(slackConnection);
		JSONArray channelsArray = new JSONArray();
		
		try {
			channelsArray = result.getJSONArray("channels");
			for(int i=0;i<channelsArray.length();i++) {
				String channelName = "#"+channelsArray.getJSONObject(i).getString("name");
				//String channelID = channelsArray.getJSONObject(i).getString("id");
				
				channelList.add(channelName);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return channelList;
	}

	private JSONObject sendMessageReadResponse(HttpURLConnection connection, String dataPayload) {
		JSONObject response = new JSONObject();

		try {
			OutputStream os = connection.getOutputStream();
			byte[] input = dataPayload.getBytes();
			os.write(input, 0, input.length);

			os.flush();
			os.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuilder responseBuilder = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				responseBuilder.append(responseLine.trim());
			}
			System.out.println("SLACK RESPONSE --- >>> " + responseBuilder.toString()); // TODO delete

			response = new JSONObject(responseBuilder.toString());
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	private JSONObject getChannelsList(HttpURLConnection connection) {
		JSONObject response = new JSONObject();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuilder responseBuilder = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				responseBuilder.append(responseLine.trim());
			}

			response = new JSONObject(responseBuilder.toString());

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
}