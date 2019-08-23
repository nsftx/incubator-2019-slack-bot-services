package com.welcome.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WelcomeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomeBotApplication.class, args);
	}
}