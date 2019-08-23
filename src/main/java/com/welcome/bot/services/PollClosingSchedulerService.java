package com.welcome.bot.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.welcome.bot.domain.Poll;
import com.welcome.bot.slack.api.SlackClientApi;
import com.welcome.bot.slack.api.customexceptionhandler.SlackApiException;

@Component
public class PollClosingSchedulerService {

	@Autowired
	SlackClientApi slackService;
	@Autowired
	PollService pollService;

	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskUsingCronExpression() throws SlackApiException {
		// TEST
		System.out.println("SCHEDULED CRON - " + new Date());

		List<Poll> pollList = pollService.getActivePolls();

		String text = "Thank You for voting! POLL is closed :)";
		if(!pollList.isEmpty()) {
			pollService.deactivatePollsByList(pollList);
			for(Poll p : pollList) {
				try {
					slackService.updateMessage(p.getChannel(), text, p.getSlackTimestamp());
				} catch (SlackApiException e) {
					throw new SlackApiException("Poll closing failed. Cause: " + e.getMessage());
				}
			}
		}
	}
}