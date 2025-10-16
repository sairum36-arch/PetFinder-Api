package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.messages.MessageResponse;
import com.PetFinder.PetFinder.dto.messages.MessageSendRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.service.IncidentMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents/{incidentId}/messages")
@RequiredArgsConstructor
public class IncidentMessageController {
    private final IncidentMessageService messageService;

    @PostMapping
    public MessageResponse sendMessage(@PathVariable Long incidentId, @RequestBody MessageSendRequest request, @AuthenticationPrincipal CredentialEntity currentUser){
        return messageService.sendMessage(incidentId, request, currentUser);
    }

    @GetMapping
    public List<MessageResponse> getMessages(
            @PathVariable Long incidentId,
            @AuthenticationPrincipal CredentialEntity currentUser) {
        return messageService.getMessages(incidentId, currentUser);
    }
}

