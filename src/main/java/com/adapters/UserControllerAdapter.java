package com.adapters;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domain.User;
import com.ports.UserControllerPort;
import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerAdapter implements UserControllerPort{
	@Autowired
	UserService userService;
	
	@Override
	public User AddUser(@RequestBody User user) {
		return userService.AddUser(user);
	}
	
	@Override
	public Iterable<User> GetUsers() {
		return userService.GetUsers();
	}

	@Override
	public User GetUser(@PathVariable Integer id) {
		return userService.GetUser(id);
	}
	
	@Override
	public User UpdateUser(@PathVariable Integer id, @RequestBody User user) {
		return userService.UpdateUser(id, user);
	}
	
	@Override
	public User DeleteUser(@PathVariable Integer id) {
		return userService.DeleteUser(id);
	}
		
//localhost:8080/add?name=amer&surname=asd&email=asdas&rola=asdasd

}
