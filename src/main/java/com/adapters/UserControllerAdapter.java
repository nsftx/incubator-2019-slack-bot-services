package com.adapters;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domain.User;
import com.repository.UserRepository;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class UserControllerAdapter{
	@Autowired
	UserRepository userRepository;
	
	
	@PostMapping("/api/user")
	public User AddUser(@RequestBody User user) {
		
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/api/user")
	public Iterable<User> GetUsers(Pageable pageable) {
		Page<User> usersPage = userRepository.findAll(pageable);
		return usersPage;
	}
	
	@GetMapping("/api/user/{id}")
	public User GetUser(@PathVariable Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No data!"));
		return user;
	}
	
	@PutMapping("/api/user/{id}")
	public User UpdateUser(@PathVariable Integer id, @RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	@DeleteMapping("/api/user/{id}")
	public User DeleteUser(@PathVariable Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User data not found"));
		userRepository.deleteById(id);
		return user;
	}
		
//localhost:8080/add?name=amer&surname=asd&email=asdas&rola=asdasd

}
