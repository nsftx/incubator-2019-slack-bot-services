package com.welcome.bot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.welcome.bot.domain.Audit;

public interface AuditRepository extends JpaRepository<Audit, Integer>{
	Page<Audit> findAll(Pageable pageParam);
}
