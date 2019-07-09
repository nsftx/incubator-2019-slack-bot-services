package com.example.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("repository") 
@EntityScan("model")
@SpringBootApplication
@ComponentScan(basePackages="controller")
public class WelcomeBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(WelcomeBotApplication.class, args);
	}

}
