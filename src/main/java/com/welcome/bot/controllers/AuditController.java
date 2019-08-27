package com.welcome.bot.controllers;

import java.time.Duration;

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

	@Autowired
	AuditService auditService;
	
	@GetMapping("/api/logs")

	public Page<AuditDTO> getAllLogs(Pageable pageable, @CurrentUser UserPrincipal userPrincipal){
		return auditService.getAllLogs(pageable, userPrincipal);
	}
	
	// SSE TEST
	@GetMapping(path = "/api/logs/new-logs-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<String>> emitEvents(){
		return Flux.interval(Duration.ofMinutes(3))
			      .map(sequence -> ServerSentEvent.<String> builder()
			        .id(String.valueOf(sequence))
			          .event("new-log-event")
			          .data(String.valueOf(AuditService.NEW_LOGS_COUNT))
			          .build());
	}
}