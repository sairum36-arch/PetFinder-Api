package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Notification.NotificationResponse;
import com.PetFinder.PetFinder.entity.NotificationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel =  "spring")
public interface NotificationMapper {
    NotificationResponse toDto(NotificationEntity entity);

    List<NotificationResponse> toDtoList(List<NotificationEntity> entities);
}
