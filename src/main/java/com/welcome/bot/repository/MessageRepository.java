package com.welcome.bot.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.welcome.bot.domain.Message;
import com.welcome.bot.domain.User;

public interface MessageRepository extends JpaRepository<Message, Integer>{

//    @Query("FROM Message WHERE title = ?1")
//    List<Message> getMessagesByTitle(String title);

	List<Message> findAllByTitle(String title);
	
    Page<Message> findAll(Pageable pageParam);
    
    Page<Message> findAllByUser(Pageable pageParam, User user);
    
    Page<Message> findAllByUserAndDeleted(Pageable pageParam, User user, boolean deleted);
    
    Page<Message> findAllByTitle(String title, Pageable pageParam);
    
    Page<Message> findAllByDeleted(Pageable pageParam, boolean deleted);
    
    List<Message> findAllByCreatedAtLessThan(Date date);
    
}
