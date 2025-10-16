package com.PetFinder.PetFinder.dto.messages;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Long id;
    private String messageText;
    private LocalDateTime sentAt;
    private SenderInfo sender;
}
