package com.PetFinder.PetFinder.mapper;


import com.PetFinder.PetFinder.dto.auth.AuthResponse;
import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.mainProfile.UserProfileResponse;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.service.PetService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface UserMapper {
    @Mapping(source = "userEntity.id", target = "userId")
    @Mapping(source = "userEntity.name", target = "name")
    @Mapping(source = "userEntity.credentialEntity.email", target = "email")
    @Mapping(source = "token", target = "token")
    AuthResponse toAuthResponse(UserEntity userEntity, String token);
    @Mapping(target = "pets", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "credentialEntity.email", target = "email")
    @Mapping(target = "activeCollarsCount", expression = "java(userEntity.getPetEntities() != null ? userEntity.getPetEntities().size() : 0)")
    UserProfileResponse toUserProfileResponse(UserEntity userEntity);
}
