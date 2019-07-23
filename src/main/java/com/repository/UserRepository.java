package com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer>{
	//Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
	
	Page<User> findAll(Pageable pageable);
	
}
