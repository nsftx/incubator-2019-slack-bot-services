package com.welcome.bot.slack.api.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateOperator {

	public DateOperator() {}

	public String convertToEpoch(Date date) {
		String epochTime = String.valueOf(date.getTime()/1000);
		return epochTime;
	}

	public List<Date> generateRepeatTimes(Date date){
		List<Date> repeatDates = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int weekDay = c.get(Calendar.DAY_OF_WEEK);

		int i = c.get(Calendar.DAY_OF_MONTH);
		int limit = i+90;
		for(int j=i;j<limit;j++) {
			c.set(year, month, j);
			int nextWeekDay = c.get(Calendar.DAY_OF_WEEK);
			if(nextWeekDay == weekDay) {
				repeatDates.add(c.getTime());
			}
		}
		return repeatDates;
	}
}