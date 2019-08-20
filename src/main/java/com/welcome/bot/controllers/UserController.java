package com.welcome.bot.controllers;





import org.springframework.web.bind.annotation.GetMapping;





import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.welcome.bot.domain.User;
import com.welcome.bot.domain.UserSettings;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.exception.base.BaseException;
import com.welcome.bot.models.UserDTO;
import com.welcome.bot.payload.ApiResponse;
import com.welcome.bot.payload.RegistrationRequest;
import com.welcome.bot.payload.TranslationSettings;
import com.welcome.bot.repository.InviteRepository;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.repository.UserSettingsRepository;
import com.welcome.bot.security.CurrentUser;
import com.welcome.bot.security.UserPrincipal;
import com.welcome.bot.services.InviteService;
import com.welcome.bot.services.UserService;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.net.URI;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;



@RestController
@RequestMapping("/user")
public class UserController {

	 private final InviteService inviteService;
	 private final UserRepository userRepository;
         private final UserSettingsRepository userSettingsRepository;
         private final MessageSource messageSource;
         private final InviteRepository inviteRepository;
         private final UserService userService;
	
	 public InviteService(final InviteService inviteService) {
         this.inviteService = inviteService;
        }
	 public UserRepository(final UserRepository userRepository) {
         this.userRepository = userRepository;
         }
	 public UserSettingsRepository(final UserSettingsRepository userSettingsRepository) {
         this.userSettingsRepository = userSettingsRepository;
         }
	 public InviteRepository(final InviteRepository inviteRepository) {
         this.inviteRepository = inviteRepository;
         }
	 public MessageSource(final MessageSource messageSource) {
         this.messageSource= messageSource;
         }
	public UserService(final UserService userService) {
         this.userService= userService;
         }

    
    @GetMapping("/translation")
    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    public TranslationSettings translate() {
 
    	TranslationSettings data=new TranslationSettings();
    	data.setSettings(messageSource.getMessage("settings", null, LocaleContextHolder.getLocale()));
    	data.setTheme(messageSource.getMessage("theme", null, LocaleContextHolder.getLocale()));
    	data.setSelectColor(messageSource.getMessage("select.color", null, LocaleContextHolder.getLocale()));
    	data.setSelectLanguage(messageSource.getMessage("select.language", null, LocaleContextHolder.getLocale()));
    	data.setLanguage(messageSource.getMessage("language", null, LocaleContextHolder.getLocale()));
    	data.setSave(messageSource.getMessage("save", null, LocaleContextHolder.getLocale()));
        data.setChannel(messageSource.getMessage("channel",null,LocaleContextHolder.getLocale()));
	data.setMessages(messageSource.getMessage("messages",null,LocaleContextHolder.getLocale()));
	data.setMessage(messageSource.getMessage("message",null,LocaleContextHolder.getLocale()));
	data.setSchedules(messageSource.getMessage("schedules",null,LocaleContextHolder.getLocale()));
	data.setUsers(messageSource.getMessage("users",null,LocaleContextHolder.getLocale()));
	data.setActiveAt(messageSource.getMessage("activeAt",null,LocaleContextHolder.getLocale()));
	data.setTriggers(messageSource.getMessage("triggers",null,LocaleContextHolder.getLocale()));
	data.setTrigger(messageSource.getMessage("trigger",null,LocaleContextHolder.getLocale()));
	data.setName(messageSource.getMessage("name",null,LocaleContextHolder.getLocale()));
	data.setRole(messageSource.getMessage("role",null,LocaleContextHolder.getLocale()));
	data.setNextRun(messageSource.getMessage("nextRun",null,LocaleContextHolder.getLocale()));
	data.setTitle(messageSource.getMessage("title",null,LocaleContextHolder.getLocale()));
	data.setText(messageSource.getMessage("text",null,LocaleContextHolder.getLocale()));
	data.setActive(messageSource.getMessage("active",null,LocaleContextHolder.getLocale()));
	data.setRepeat(messageSource.getMessage("active",null,LocaleContextHolder.getLocale()));

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
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDTO> getAllUsers(Pageable pageable) {
    	return userService.getAllUsers(pageable);
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
    	  public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest signUpRequest) {
    	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
    	            throw new BaseException("Email address already in use.");
    	        }

    	       
    	        User user = new User(signUpRequest.getEmail());
    	       
    	        user.setRole(signUpRequest.getRole());
    	        inviteRepository.save(user.getInvite());
    	        userSettingsRepository.save(user.getUserSettings());

    	        User result = userRepository.save(user);
    	        if(inviteService.sendInvite(result.getEmail())) {
    	        
    	        result.getInvite().setSent(true);
    	        }
    	        else {
    	        	
    	        }
    	       
    	        
    	        
    	        URI location = ServletUriComponentsBuilder
    	                .fromCurrentContextPath().path("/user/me")
    	                .buildAndExpand(result.getId()).toUri();

    	        return ResponseEntity.created(location)
    	                .body(new ApiResponse(true, "User registered successfully@"));
    	    }

   
    
    
}

