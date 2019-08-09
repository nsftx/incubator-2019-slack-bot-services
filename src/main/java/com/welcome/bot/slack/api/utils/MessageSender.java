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

import com.welcome.bot.slack.api.model.interactionpayload.Channel;

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
			messageID = result.getString("scheduled_message_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(messageID.isEmpty() || messageID == null) { 
			return null;
		}
		
		return messageID;
	}

	public List<String> sendRequestToGetSchedulesList(HttpURLConnection slackConnection){
		List<String> schedulesList = new ArrayList<>();
		JSONObject result = getChannelsOrSchedulesList(slackConnection);
		JSONArray schedulesArray = new JSONArray();

		try {
			schedulesArray = result.getJSONArray("scheduled_messages");
			for(int i=0;i<schedulesArray.length();i++) {
				String scheduleID = schedulesArray.getJSONObject(i).getString("id");
				schedulesList.add(scheduleID);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return schedulesList;
	}

	public List<Channel> sendRequestToGetChannelsList(HttpURLConnection slackConnection) {
		List<Channel> channelList = new ArrayList<>();
		JSONObject result = getChannelsOrSchedulesList(slackConnection);
		JSONArray channelsArray = new JSONArray();

		try {
			channelsArray = result.getJSONArray("channels");
			for(int i=0;i<channelsArray.length();i++) {
				String channelName = "#"+channelsArray.getJSONObject(i).getString("name");
				String channelID = channelsArray.getJSONObject(i).getString("id");
				Channel c = new Channel();
				c.setName(channelName);
				c.setId(channelID);
				channelList.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return channelList;
	}

	private JSONObject sendMessageReadResponse(HttpURLConnection connection, String dataPayload) {
		JSONObject response = new JSONObject();

		try {
			connection.connect(); //TODO test - delete later
			
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
			System.out.println("SLACK RESPONSE --- >>> " + responseBuilder.toString()); //TODO test - delete later

			response = new JSONObject(responseBuilder.toString());
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}

	private JSONObject getChannelsOrSchedulesList(HttpURLConnection connection) {
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