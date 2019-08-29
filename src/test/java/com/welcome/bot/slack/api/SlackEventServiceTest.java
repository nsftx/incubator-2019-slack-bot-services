package com.welcome.bot.slack.api;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import com.welcome.bot.slack.api.model.eventpayload.EventItem;
import com.welcome.bot.slack.api.model.eventpayload.EventPayload;

class SlackEventServiceTest {

	@InjectMocks
	private SlackEventService slackEventService;

	@Mock
	private ApplicationEventPublisher appEventPublisher;
	@Mock
	private EventPayload eventPayload;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		eventPayload = new EventPayload();
	}

	@Test
	void givenEventIsVerificationCheckReturnIsString() {
		eventPayload.setChallenge("challenge");
		eventPayload.setType("url_verification");

		String response = slackEventService.handleEvent(eventPayload);
		assertEquals("challenge", response);
	}

	@Test
	void givenEventIsCallbackCheckReturnIsNull() {
		eventPayload.setType("event_callback");
		EventItem eventItem = new EventItem();
		eventItem.setUser("tester");
		eventItem.setType("app_mention");
		eventPayload.setEventItem(eventItem);

		String response = slackEventService.handleEvent(eventPayload);
		assertEquals(null, response);
	}
}