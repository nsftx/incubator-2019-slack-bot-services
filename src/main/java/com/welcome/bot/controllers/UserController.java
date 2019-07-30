package com.welcome.bot.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.welcome.bot.domain.User;
import com.welcome.bot.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;



@RestController
public class UserController{

	@Autowired
	UserService userService;
	
	@PostMapping("/api/users")
	public User AddUser(@RequestBody User user) {
		return userService.AddUser(user);
	}

	@GetMapping("/api/users")
	public Iterable<User> GetUsers(Pageable pageable) {
		return userService.GetUsers(pageable);
	}
	
	@GetMapping("/api/users/{id}")
	public User GetUser(@PathVariable Integer id) {
		return userService.GetUser(id);
	}
	
	@PutMapping("/api/users/{id}")
	public User UpdateUser(@PathVariable Integer id, @RequestBody User user) {
		return userService.UpdateUser(id, user);
	}
	
	@DeleteMapping("/api/users/{id}")
	public User DeleteUser(@PathVariable Integer id) {
		return userService.DeleteUser(id);
	}
	
}
