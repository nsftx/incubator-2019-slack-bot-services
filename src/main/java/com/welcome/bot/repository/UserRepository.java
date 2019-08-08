package com.welcome.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.welcome.bot.domain.User;


public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
	Page<User> findAll(Pageable pageable);
	
}
