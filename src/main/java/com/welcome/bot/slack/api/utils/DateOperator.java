package com.welcome.bot.slack.api.utils;

import java.util.Calendar;
import java.util.Date;

public class DateOperator {
	
	public DateOperator() {}

	public String convertToReminder(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		int dayIndex = c.get(Calendar.DAY_OF_WEEK);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		String day = null;
		switch(dayIndex) {
		case(1): 
			day = "Sunday";
			break;
		case(2):
			day = "Monday";
			break;
		case(3):
			day = "Tuesday";
			break;
		case(4):
			day = "Wednesday";
			break;
		case(5):
			day = "Thursday";
			break;
		case(6):
			day = "Friday";
			break;
		case(7):
			day = "Saturday";
			break;
		}
		String reminder = "Every " + day + " at " + hour + ":" + minute;
		return reminder;
	}
	
	public String convertToEpoch(Date date) {
		String epochTime = String.valueOf(date.getTime()/1000);
		return epochTime;
	}
}