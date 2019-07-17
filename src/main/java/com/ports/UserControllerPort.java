package com.ports;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.domain.User;

public interface UserControllerPort {
	@PostMapping("/api/user")
	public User AddUser(@RequestBody User user);
	
	@GetMapping("/api/user")
	public Iterable<User> GetUsers();

	@GetMapping("/api/user/{id}")
	public User GetUser(@PathVariable Integer id);
	
	@PutMapping("/api/user/{id}")
	public User UpdateUser(@PathVariable Integer id, @RequestBody User user);
	
	@DeleteMapping("/api/user/{id}")
	public User DeleteUser(@PathVariable Integer id);
	
}
