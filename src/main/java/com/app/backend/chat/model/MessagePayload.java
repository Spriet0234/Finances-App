package com.app.backend.chat.model;

import java.util.UUID;

public class MessagePayload {

    private UUID chatSessionId;
    private String content;

    public UUID getChatSessionId() {
        return chatSessionId;
    }
    public void setChatSessionId(UUID chatSessionId) {
        this.chatSessionId = chatSessionId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
