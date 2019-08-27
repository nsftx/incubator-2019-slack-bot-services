package com.welcome.bot.controllers;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.models.AuditDTO;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.services.AuditService;

import reactor.core.publisher.Flux;

@RestController
public class AuditController {

	public static int NEW_LOGS_COUNT = 0;


	@Autowired
	AuditService auditService;
	
	@GetMapping("/api/logs")

	public Page<AuditDTO> getAllLogs(Pageable pageable, @CurrentUser UserPrincipal userPrincipal){
		return auditService.getAllLogs(pageable, userPrincipal);

	}
	
	// SSE TEST
	@GetMapping(path = "/api/logs/new-logs-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<String>> emitEvents(){
		return Flux.interval(Duration.ofMinutes(1))
			      .map(sequence -> ServerSentEvent.<String> builder()
			        .id(String.valueOf(sequence))
			          .event("new-log-event")
			          .data("New Log Count is: " + String.valueOf(++NEW_LOGS_COUNT) + " .Stream Every 1minute. " + " TIME IS-> " + LocalTime.now().toString())
			          .build());
	}
}