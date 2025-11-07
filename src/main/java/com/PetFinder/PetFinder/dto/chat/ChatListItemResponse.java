package com.PetFinder.PetFinder.dto.chat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatListItemResponse {
    private Long incidentId;
    private String petName;
    private String petMainPhotoUrl;
    private String lastMessageText;
    private LocalDateTime lastMessageSentAt;
    private Long lastMessageSenderId;
    private int unreadMessagesCount;
}
