package com.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
//    neka stoji ovdje sad za sad
//    @Query("FROM Message WHERE title = ?1")
//    List<Message> getMessagesByTitle(String title);
    
    Page<Message> findAll(Pageable pageParam);
    
    Page<Message> findAllByTitle(String title, Pageable pageParam);
    
    
}
