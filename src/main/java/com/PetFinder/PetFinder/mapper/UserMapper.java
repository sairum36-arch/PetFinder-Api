package com.PetFinder.PetFinder.mapper;


import com.PetFinder.PetFinder.dto.auth.AuthResponse;
import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import com.PetFinder.PetFinder.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PetMapper.class, GeoFenceMapper.class})
public interface UserMapper {
    @Mapping(source = "pets", target = "pets")
    @Mapping(target = "activeCollarsCount", expression = "java(user.getPets().size())")
    UserProfileResponse toUserProfileResponse(User user);
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "token", target = "token")
    AuthResponse toAuthResponse(User user, String token);
    User toEntity(UserProfileResponse userProfileResponse);
}
