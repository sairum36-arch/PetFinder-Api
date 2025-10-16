package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Incident.*;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.service.FileStoragePackage.UrlBuilderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, UserMapper.class, UrlBuilderService.class})
public interface IncidentMapper {
    @Mapping(target = "ownersNotes", source = "ownerNotes")
    IncidentEntity toEntity(IncidentCreateRequest dto);

    @Mapping(source = "petEntity", target = "petInfo")
    @Mapping(source = "petEntity.userEntity", target = "ownerInfo")
    IncidentDetailResponse toDetailResponse(IncidentEntity entity);

    @Mapping(source = "petEntity", target = "pet")
    @Mapping(target = "distanceToUserKm", ignore = true)
    IncidentBriefResponse toBriefResponse(IncidentEntity entity);

    @Mapping(source = "breedEntity.name", target = "breedName")
    @Mapping(source = "userEntity.id", target = "ownerId")
    @Mapping(source = ".", target = "mainPhotoUrl")
    PetBriefInfo petEntityToPetBriefInfo(PetEntity petEntity);

    @Mapping(source = ".", target = "mainPhotoUrl")
    PetInfo petEntityToPetInfo(PetEntity petEntity);

    @Mapping(source = ".", target = "avatarUrl")
    UserInfo userEntityToUserInfo(UserEntity userEntity);
}