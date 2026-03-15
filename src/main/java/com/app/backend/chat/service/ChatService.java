package com.app.backend.chat.service;

import com.app.backend.chat.model.ChatSession;
import com.app.backend.chat.model.Message;
import com.app.backend.chat.repository.ChatRepository;
import com.app.backend.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    public ChatSession createSession(UUID userId) {
        ChatSession session = new ChatSession();
        session.setUserId(userId);
        session.setTitle("New Chat");
        return chatRepository.save(session);
    }

    public ChatSession getSession(UUID sessionId) {
        return chatRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Chat session not found"));
    }

    public List<ChatSession> getUserSessions(UUID userId) {
        return chatRepository.findByUserId(userId);
    }

    public Message saveMessage(UUID chatSessionId, String role, String content) {
        Message message = new Message();
        message.setChatSessionId(chatSessionId);
        message.setRole(role);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public List<Message> getSessionMessages(UUID chatSessionId) {
        return messageRepository.findByChatSessionIdOrderByCreatedAtAsc(chatSessionId);
    }
}