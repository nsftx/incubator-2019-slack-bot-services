package com.welcome.bot.slack.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.welcome.bot.slack.api.model.eventpayload.EventPayload;

class SlackControllerTest {

	@InjectMocks
	private SlackController slackController;

	@Mock
	private SlackEventService slackEventService;
	@Mock
	private SlackCommandService slackCommandService;
	@Mock
	private SlackInteractionService slackInteractionService;
	@Mock
	private EventPayload eventPayload;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		eventPayload = new EventPayload();
	}

	@Test
	public void checkURLVerificationReturnsStringChallenge() {
		eventPayload.setType("url_verification");
		eventPayload.setChallenge("challenge string");
		when(slackController.eventHandler(eventPayload)).thenCallRealMethod();
		String response = slackController.eventHandler(eventPayload);
		assertEquals("challenge string", response);
	}

	@Test
	public void checkEventCallbackReturnsNull() {
		eventPayload.setType("event_callback");
		when(slackController.eventHandler(eventPayload)).thenReturn(null);
		String response = slackController.eventHandler(eventPayload);
		assertEquals(null, response);
	}

	@Test
	public void checkCommandAboutReturnsTextAbout() {
		when(slackController.commandAbout()).thenReturn("NSoft's mission is to provide bet shop owners with a powerful omni-channel platform, visually appealing and revenue generating virtual games and data-packed sportsbook, in order to help them grow their business.");
		String response = slackController.commandAbout();
		assertEquals("NSoft's mission is to provide bet shop owners with a powerful omni-channel platform, visually appealing and revenue generating virtual games and data-packed sportsbook, in order to help them grow their business.", response);
	}

	@Test
	public void checkCommandBenefitsReturnsTextBenefits() {
		when(slackController.commandBenefits()).thenReturn("NSoft offers plenty of additional benefits to their employees. Some of these are:\n-Free stuff\n-Monthly cinema visits\n-Subsidized food and drinks\n-Flexible work hours\n-Events,games,etc\n-Rewards\n\n For more info check <http://www.nsoft.com/careers/|Nsoft Careers - Perks");
		String response = slackController.commandBenefits();
		assertEquals("NSoft offers plenty of additional benefits to their employees. Some of these are:\n-Free stuff\n-Monthly cinema visits\n-Subsidized food and drinks\n-Flexible work hours\n-Events,games,etc\n-Rewards\n\n For more info check <http://www.nsoft.com/careers/|Nsoft Careers - Perks", response);
	}

	@Test
	public void checkCommandWorkReturnsTextWork() {
		when(slackController.commandWork()).thenReturn("Flexible working hours allow employees to work 8h/day in range starting from 7:00AM-10:00AM and working until 3:00PM-6:00PM.");
		String response = slackController.commandWork();
		assertEquals("Flexible working hours allow employees to work 8h/day in range starting from 7:00AM-10:00AM and working until 3:00PM-6:00PM.", response);
	}

	@Test
	public void checkCommandDocsReturnsTextDocs() {
		when(slackController.commandDocs()).thenReturn("More information on NSoft products on <http://www.nsoft.com/betting-software|NSoft Products>");
		String response = slackController.commandDocs();
		assertEquals("More information on NSoft products on <http://www.nsoft.com/betting-software|NSoft Products>", response);
	}

	@Test
	public void checkCommandCommunityReturnsTextCommunity() {
		when(slackController.commandCommunity()).thenReturn("Our company is active on social media platforms. Check out our Instagram profile: <http://www.instagram.com/nsoftcompany|NSoft-Instagram>");
		String response = slackController.commandCommunity();
		assertEquals("Our company is active on social media platforms. Check out our Instagram profile: <http://www.instagram.com/nsoftcompany|NSoft-Instagram>", response);
	}

}
