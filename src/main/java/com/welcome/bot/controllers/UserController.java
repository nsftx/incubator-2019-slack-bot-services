package com.welcome.bot.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.welcome.bot.domain.User;
import com.welcome.bot.domain.UserSettings;
import com.welcome.bot.exception.BadRequestException;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.payload.ApiResponse;
import com.welcome.bot.payload.SignUpRequest;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.repository.UserSettingsRepository;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;



@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSettingsRepository userSettingsRepository;
    @Autowired
    MessageSource messageSource;
    
    @RequestMapping(value = "/translation", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    public List<String> greeting() {
    	List<String> data = new ArrayList<String>();
    	data.add(messageSource.getMessage("settings", null, LocaleContextHolder.getLocale()));
    	data.add(messageSource.getMessage("theme", null, LocaleContextHolder.getLocale()));
    	data.add(messageSource.getMessage("select.color", null, LocaleContextHolder.getLocale()));
    	data.add(messageSource.getMessage("select.language", null, LocaleContextHolder.getLocale()));
    	data.add(messageSource.getMessage("language", null, LocaleContextHolder.getLocale()));
     return data;
    }
    
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
    @GetMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@RequestBody User user) {
    	userRepository.delete(user);
    		
    }
   
   
    @PostMapping("/userSettings")
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    public UserSettings setUserSettings( @RequestBody UserSettings userSettings,@CurrentUser UserPrincipal userPrincipal) {
    	User user=userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    	UserSettings usersettings2=userSettingsRepository.findById(user.getUserSettings().getId()).orElseThrow(() -> new ResourceNotFoundException("UserSettings", "id", userSettings.getId()));
    	usersettings2.setTheme(userSettings.getTheme());
    	usersettings2.setLanguage(userSettings.getLanguage());
    	UserSettings result=userSettingsRepository.save(usersettings2);
    	return result;
    	
    	
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
    	            throw new BadRequestException("Email address already in use.");
    	        }

    	       
    	        User user = new User(signUpRequest.getEmail());
    	        user.setRole(signUpRequest.getRole());
    	        User result = userRepository.save(user);
    	        SimpleMailMessage msg = new SimpleMailMessage();
    	        msg.setTo(result.getEmail());
    	        msg.setSubject("You've been addedd to NSoft Welcome Bot application");
    	        msg.setText("Hello"+user.getEmail()+"\n You've been added to Nsoft Welcome Bot application. You can login to application here: http://nsoft.com/welcome-bot/login \n Please login with this email and google password.");

    	        javaMailSender.send(msg);
    	        
    	        URI location = ServletUriComponentsBuilder
    	                .fromCurrentContextPath().path("/user/me")
    	                .buildAndExpand(result.getId()).toUri();

    	        return ResponseEntity.created(location)
    	                .body(new ApiResponse(true, "User registered successfully@"));
    	    }

   
    
    
}

