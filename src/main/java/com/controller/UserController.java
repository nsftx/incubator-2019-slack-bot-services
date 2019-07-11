package com.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.model.User;
import com.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/users")
	public User AddUser(@RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/users")
	public Iterable<User> GetUsers() {
		Iterable<User> users = userRepository.findAll();
		return users;
	}

	@GetMapping("/users/{id}")
	public User GetUser(@PathVariable Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No data!"));
		return user;
	}
	
	@PutMapping("/users/{id}")
	public void UpdateUser(@PathVariable Integer id, @RequestBody User user) {
		userRepository.save(user);
	}
	
	@DeleteMapping("/users/{id}")
	public void DeleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
	}
		
//localhost:8080/add?name=amer&surname=asd&email=asdas&rola=asdasd

}
