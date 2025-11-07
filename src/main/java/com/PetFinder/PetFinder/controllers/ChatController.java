package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.chat.ChatListItemResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public List<ChatListItemResponse> getMyChatList(@AuthenticationPrincipal CredentialEntity currentUser){
        return chatService.getChatListForUser(currentUser);
    }
}
