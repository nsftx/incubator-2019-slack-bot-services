package com.example.springsocial.repository;

import com.example.springsocial.model.User;
import com.example.springsocial.model.UserSettings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    Optional<UserSettings> findById(String id);


}
