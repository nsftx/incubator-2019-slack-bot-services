package com.example.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.repository")
@EntityScan("com.domain")
//@ComponentScan(basePackages="com.controller")
@ComponentScan("com")

@SpringBootApplication
public class WelcomeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomeBotApplication.class, args);
	}
}
