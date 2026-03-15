package com.app.backend.chat.repository;

import com.app.backend.chat.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<ChatSession, UUID> {

    List<ChatSession> findByUserId(UUID userId);
}