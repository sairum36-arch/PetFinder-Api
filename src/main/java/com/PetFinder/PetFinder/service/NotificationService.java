package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Notification.NotificationResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.NotificationEntity;
import com.PetFinder.PetFinder.entity.NotificationType;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.NotificationMapper;
import com.PetFinder.PetFinder.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsForUser(CredentialEntity currentUser){
        List<NotificationEntity> notifications = notificationRepository.findAllByUserSortedByDate(currentUser.getUserEntity());
        return notificationMapper.toDtoList(notifications);
    }

    @Transactional
    public void markAsRead(Long notificationId, CredentialEntity currentUser) {
        NotificationEntity notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Уведомление с id " + notificationId + " не найдено"));
        if (!notification.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("У вас нет прав для изменения этого уведомления.");
        }
        if (!notification.getIsRead()) {
            notification.setIsRead(true);
        }
    }

    @Transactional
    public void createNotification(UserEntity user, NotificationType type, String message){
        NotificationEntity notification = notificationMapper.toEntity(user, type, message);
        NotificationEntity savedNotification = notificationRepository.save(notification);
        String destination = "/topic/notifications/" + user.getId();
        NotificationResponse payload = notificationMapper.toDto(savedNotification);
        messagingTemplate.convertAndSend(destination, payload);
    }
}
