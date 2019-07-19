package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	
}
