package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;

import com.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("FROM Message WHERE title = ?1")
    List<Message> getMessagesByTitle(String title);
}
