package com.welcome.bot.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.welcome.bot.slack.api.utils.ConnectionGenerator;
import com.welcome.bot.slack.api.utils.MessageSender;
import com.welcome.bot.slack.api.utils.PayloadGenerator;

@Configuration
public class AppConfiguration {

	   @Bean
	   public ModelMapper modelMapper() {
	      ModelMapper modelMapper = new ModelMapper();
	      return modelMapper;
	   }
	   
	   @Bean // Slack/API
	   public ConnectionGenerator connectionGen() {
		   return new ConnectionGenerator();
	   }
	   
	   @Bean // Slack/API
	   public PayloadGenerator payloadGen() {
		   return new PayloadGenerator();
	   }
	   
	   @Bean // Slack/API
	   public MessageSender sender() {
		   return new MessageSender();
	   }
	   
	   @Bean // Slack/API
	   public ObjectMapper jsonMapper() {
		   return new ObjectMapper();
	   }
}