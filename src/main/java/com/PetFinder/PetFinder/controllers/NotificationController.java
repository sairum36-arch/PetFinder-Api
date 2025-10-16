package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.Notification.NotificationResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getMyNotifications(@AuthenticationPrincipal CredentialEntity currentUser){
        return notificationService.getNotificationsForUser(currentUser);
    }

    @PostMapping("/{id}/read")
    public void markNotificationAsRead(@PathVariable("id") Long notificationId, @AuthenticationPrincipal CredentialEntity currentUser){
        notificationService.markAsRead(notificationId, currentUser);
    }
}
