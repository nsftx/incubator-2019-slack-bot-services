package com.welcome.bot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.welcome.bot.domain.User;
import com.welcome.bot.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User AddUser(@RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	public Iterable<User> GetUsers(Pageable pageable) {
		Page<User> usersPage = userRepository.findAll(pageable);
		return usersPage;
	}
	
	public User GetUser(@PathVariable Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No data!"));
		return user;
	}
	
	public User UpdateUser(@PathVariable Integer id, @RequestBody User user) {
		userRepository.save(user);
		return user;
	}
	
	public User DeleteUser(@PathVariable Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User data not found"));
		userRepository.deleteById(id);
		return user;
	}
	
}
