package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.messages.MessageResponse;
import com.PetFinder.PetFinder.dto.messages.SenderInfo;
import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.service.FileStoragePackage.UrlBuilderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UrlBuilderService.class})
public interface IncidentMessageMapper {

    @Mapping(source = "sender", target = "sender")
    MessageResponse toDto(IncidentMessageEntity entity);

    List<MessageResponse> toDtoList(List<IncidentMessageEntity> entities);

    @Mapping(source = ".", target = "avatarUrl")
    SenderInfo userEntityToSenderInfo(UserEntity entity);
}
