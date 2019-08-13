package com.welcome.bot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.welcome.bot.domain.Invite;
import com.welcome.bot.domain.UserSettings;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    Optional<Invite> findById(Long id);


}
