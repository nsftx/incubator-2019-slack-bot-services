package com.welcome.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.welcome.bot.domain.Audit;
import com.welcome.bot.domain.User;

public interface AuditRepository extends JpaRepository<Audit, Integer>{
	Page<Audit> findAll(Pageable pageParam);
	
	Page<Audit> findAllByUser(Pageable pageable, User user);
	
	// NOTIFICATION TODO
	// List<Audit> findAllByUserAndSeen(User user, boolean seen);
}
