package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Notification.NotificationResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.NotificationEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.NotificationMapper;
import com.PetFinder.PetFinder.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsForUser(CredentialEntity currentUser){
        List<NotificationEntity> notifications = notificationRepository.findAllByUserSortedByDate(currentUser.getUserEntity());
        return notificationMapper.toDtoList(notifications);
    }

    @Transactional
    public void markAsRead(Long notificationId, CredentialEntity currentUser) {
        log.info("Attempting to mark notification ID {} as read for user {}", notificationId, currentUser.getUsername());

        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> {
                    log.error("Notification with ID {} not found.", notificationId);
                    return new EntityNotFoundException("Уведомление с id " + notificationId + " не найдено");
                });

        log.debug("Found notification: {}", notification.getId());

        if (!notification.getUserEntity().getId().equals(currentUser.getUserId())) {
            log.warn("ACCESS DENIED: User {} (ID {}) tried to mark notification ID {} as read, but it belongs to user ID {}.",
                    currentUser.getUsername(), currentUser.getUserId(), notificationId, notification.getUserEntity().getId());
            throw new AccessDeniedException("У вас нет прав для изменения этого уведомления.");
        }

        log.debug("Access granted for user {} to notification {}. Current isRead status: {}",
                currentUser.getUsername(), notificationId, notification.getIsRead());

        if (!notification.getIsRead()) {
            notification.setIsRead(true);
            log.info("Status for notification ID {} changed to isRead=true. Transaction will now commit.", notificationId);
        } else {
            log.info("Notification ID {} was already marked as read. No changes made.", notificationId);
        }
    }
}
