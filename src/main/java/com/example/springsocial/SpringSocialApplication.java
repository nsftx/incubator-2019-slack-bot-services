package com.example.springsocial;

import com.example.springsocial.config.AppProperties;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class SpringSocialApplication {
	 
	public static void main(String[] args) {
		SpringApplication.run(SpringSocialApplication.class, args);
	}
	
}
