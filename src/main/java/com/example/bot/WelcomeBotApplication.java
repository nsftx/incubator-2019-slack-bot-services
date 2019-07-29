package com.example.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="slack.api")
public class WelcomeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomeBotApplication.class, args);
	}
}