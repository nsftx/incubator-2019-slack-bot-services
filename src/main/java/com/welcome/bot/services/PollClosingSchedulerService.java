package com.welcome.bot.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.welcome.bot.repository.PollRepository;
import com.welcome.bot.slack.api.SlackClientApi;

@Component
public class PollClosingSchedulerService {
	
	/*
     * 1) GET CURRENT DATE
     * 2) GET ALL FROM POLL REPO WHERE DATE IS < CURRENT DATE
     * 3) SLACK.UPDATE ALL THOSE WITH PAST DATE
     */
	
	@Autowired
	SlackClientApi slackService;
	@Autowired
	PollRepository pollRepo;
	@Autowired
	PollService pollService;
	
	@Scheduled(cron = "0 * * * * ?")
	public void scheduleTaskUsingCronExpression() {
		// 1)
		Date currentDate = new Date();
	    
		// TEST
		System.out.println("SCHEDULED CRON - " + currentDate);
	    
	    // 2)
//	    List<Poll> pollList = pollService.getActivePolls();
	    
	    // 3)
//	    String text = "Thank You for voting! POLL is finished :)";
//	    for(Poll p : pollList) {
//	    	if(p.getPollId() < 1) {
//	    		slackService.updateMessage(p.getChannel(), text, p.getTimestamp());
//	    	}
//	    }
	}
	
}