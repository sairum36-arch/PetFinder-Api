package com.PetFinder.PetFinder.mapper;


import com.PetFinder.PetFinder.dto.auth.AuthResponse;
import com.PetFinder.PetFinder.dto.mainProfileDTOS.GeoFenceDTO;
import com.PetFinder.PetFinder.dto.mainProfileDTOS.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import com.PetFinder.PetFinder.entity.User;
import com.PetFinder.PetFinder.service.PetService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, GeoFenceMapper.class})
public interface UserMapper {
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "geofences", ignore = true)
    @Mapping(target = "activeCollarsCount", expression = "java(user.getPets().size())")
    UserProfileResponse toBaseProfileDto(User user);
    default UserProfileResponse toFullProfile(User user, PetService petService){
        UserProfileResponse dto = toBaseProfileDto(user);
        List<PetWithCollarsStatus> petDto = petService.getPetsForUserProfile(user);
        dto.setPets(petDto);
        return dto;
    }
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "token", target = "token")
    AuthResponse toAuthResponse(User user, String token);
    User toEntity(UserProfileResponse userProfileResponse);

}
