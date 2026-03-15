package com.app.backend.chat.controller;

import com.app.backend.chat.model.ChatSession;
import com.app.backend.chat.model.Message;
import com.app.backend.chat.model.MessagePayload;
import com.app.backend.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void handleMessage(MessagePayload payload, SimpMessageHeaderAccessor headerAccessor) {

        UUID userId = (UUID) headerAccessor.getSessionAttributes().get("userId");

        chatService.saveMessage(
                payload.getChatSessionId(),
                "user",
                payload.getContent()
        );

        List<Message> history = chatService.getSessionMessages(
                payload.getChatSessionId()
        );

        String aiResponse = "response to go here";

        chatService.saveMessage(
                payload.getChatSessionId(),
                "assistant",
                aiResponse
        );

        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/messages",
                aiResponse
        );
    }

    @MessageMapping("/chat/new")
    public void createSession(SimpMessageHeaderAccessor headerAccessor) {
        UUID userId = (UUID) headerAccessor.getSessionAttributes().get("userId");
        ChatSession session = chatService.createSession(userId);

        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/session",
                session
        );
    }
}