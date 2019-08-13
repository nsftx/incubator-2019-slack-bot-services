package com.welcome.bot.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.welcome.bot.domain.User;
import com.welcome.bot.models.UserDTO;
import com.welcome.bot.repository.UserRepository;


@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
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
	
	
}
