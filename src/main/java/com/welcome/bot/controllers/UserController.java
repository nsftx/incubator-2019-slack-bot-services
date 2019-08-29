package com.welcome.bot.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.welcome.bot.domain.User;
import com.welcome.bot.domain.UserSettings;
import com.welcome.bot.exception.ResourceNotFoundException;
import com.welcome.bot.exception.base.BaseException;
import com.welcome.bot.models.MessageDTO;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
@RestController
@RequestMapping("/user")
public class UserController {

	 private final InviteService inviteService;
	 private final UserRepository userRepository;
     private final UserSettingsRepository userSettingsRepository;
     private final MessageSource messageSource;
     private final InviteRepository inviteRepository;
     private final UserService userService;
     private Properties prop = null;

 
    public UserController(InviteService inviteService, UserRepository userRepository,
			UserSettingsRepository userSettingsRepository, MessageSource messageSource,
			InviteRepository inviteRepository, UserService userService) {
		this.inviteService = inviteService;
		this.userRepository = userRepository;
		this.userSettingsRepository = userSettingsRepository;
		this.messageSource = messageSource;
		this.inviteRepository = inviteRepository;
		this.userService = userService;
}

   @GetMapping("/translation")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public Map<String, String> translate() {
		Map<String, String> data = new HashMap<String, String>();
		InputStream is = null;
		prop = null;
		try {
			this.prop = new Properties();
			is = this.getClass().getResourceAsStream("/messages_" + LocaleContextHolder.getLocale() + ".properties");
			prop.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Set<Object> keys = prop.keySet();
		for (Object k : keys) {
			String key = (String) k;
			data.put(key, this.prop.getProperty(key));
		}
		return data;

	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@DeleteMapping("/{user_id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@PathVariable Long user_id) {
		User user=userRepository.findById(user_id).orElseThrow(() -> new ResourceNotFoundException("User", "id", user_id));
		userRepository.delete(user);

	}

	@GetMapping("/getAllUsers")
	@PreAuthorize("hasRole('ADMIN')")
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		return userService.getAllUsers(pageable);
	}

	@PostMapping("/userSettings")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public UserSettings setUserSettings(@RequestBody UserSettings userSettings,@CurrentUser UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		UserSettings usersettings2 = userSettingsRepository.findById(user.getUserSettings().getId())
				.orElseThrow(() -> new ResourceNotFoundException("UserSettings", "id", userSettings.getId()));
		usersettings2.setTheme(userSettings.getTheme());
		usersettings2.setLanguage(userSettings.getLanguage());
		UserSettings result = userSettingsRepository.save(usersettings2);
		return result;
	}

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
	public UserDTO getUser(@PathVariable Long userId) {
		return userService.getUser(userId);
	}
    
	@PostMapping("/userSettings/theme")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public UserSettings setUserSettingsTheme(@RequestBody String theme,@CurrentUser UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		UserSettings usersettings2 = userSettingsRepository.findById(user.getUserSettings().getId())
				.orElseThrow(() -> new ResourceNotFoundException("UserSettings", "id", user.getUserSettings().getId()));
		usersettings2.setTheme(theme);
		UserSettings result = userSettingsRepository.save(usersettings2);
		return result;

	}
	@PostMapping("/userSettings/language")
	@PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
	public UserSettings setUserSettingsLanguage(@RequestBody String language,@CurrentUser UserPrincipal userPrincipal) {
		User user = userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
		UserSettings usersettings2 = userSettingsRepository.findById(user.getUserSettings().getId())
				.orElseThrow(() -> new ResourceNotFoundException("UserSettings", "id", user.getUserSettings().getId()));
		usersettings2.setLanguage(language);
		UserSettings result = userSettingsRepository.save(usersettings2);
		return result;

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BaseException("Email address already in use.");
		}
		User user = new User(signUpRequest.getEmail());
		user.setRole(signUpRequest.getRole());
		inviteRepository.save(user.getInvite());
		userSettingsRepository.save(user.getUserSettings());
		User result = userRepository.save(user);
		if (inviteService.sendInvite(result.getEmail())) {
			result.getInvite().setSent(true);
		}
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
	}


}
