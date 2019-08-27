package com.welcome.bot.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.User;
import com.welcome.bot.exception.message.MessageNotFoundException;
import com.welcome.bot.exception.user.UserNotFoundException;
import com.welcome.bot.models.MessageDTO;
import com.welcome.bot.models.UserDTO;
import com.welcome.bot.repository.UserRepository;

@Service
public class UserService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;

	public User AddUser(User user) {
		userRepository.save(user);
		return user;
	}

	public Iterable<User> GetUsers(Pageable pageable) {
		Page<User> usersPage = userRepository.findAll(pageable);
		return usersPage;
	}

	public User GetUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No data!"));
		return user;
	}

	public User UpdateUser(Integer id, User user) {
		userRepository.save(user);
		return user;
	}
	public Page<UserDTO> getAllUsers(Pageable pageParam) {
		Page<User> usersPage = userRepository.findAll(pageParam);

		//preparing data for mapping
		List<User> usersList = usersPage.getContent();

		//mapping message to DTO
		List<UserDTO> usersDtos = modelMapper.map(usersList, new TypeToken<List<UserDTO>>(){}.getType());

		//creating Page with DTO
		Page<UserDTO> usersDTOPage = new PageImpl<UserDTO>(usersDtos, pageParam, usersPage.getTotalElements());

		return usersDTOPage;
	}

	public User DeleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User data not found"));
		userRepository.deleteById(id);
		return user;
	}

	public UserDTO getUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		return userDTO;
	}


}
