package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Notification.NotificationResponse;
import com.PetFinder.PetFinder.entity.NotificationEntity;
import com.PetFinder.PetFinder.entity.NotificationType;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel =  "spring")
public interface NotificationMapper {
    NotificationResponse toDto(NotificationEntity entity);

    List<NotificationResponse> toDtoList(List<NotificationEntity> entities);

    @Mapping(source = "user", target = "userEntity")
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    NotificationEntity toEntity(UserEntity user, NotificationType type, String message);
}
