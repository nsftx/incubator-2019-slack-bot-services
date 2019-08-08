package com.welcome.bot.controllers;


import com.welcome.bot.exception.BadRequestException;



import com.welcome.bot.models.AuthProvider;
import com.welcome.bot.domain.User;
import com.welcome.bot.payload.ApiResponse;
import com.welcome.bot.payload.AuthResponse;
import com.welcome.bot.payload.LoginRequest;
import com.welcome.bot.payload.SignUpRequest;
import com.welcome.bot.repository.UserRepository;
import com.welcome.bot.security.TokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
	 @Autowired
	    private JavaMailSender javaMailSender;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/signup")
    	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
    	            throw new BadRequestException("Email address already in use.");
    	        }

    	        // Creating user's account
    	        User user = new User();
    	        //user.setName(signUpRequest.getName());
    	        user.setEmail(signUpRequest.getEmail());
    	        user.setRole(signUpRequest.getRole());
    	        //user.setProvider(AuthProvider.local);

    	        //user.setPassword(passwordEncoder.encode(user.getPassword()));

    	        User result = userRepository.save(user);
//emailApplication.run(args);
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
