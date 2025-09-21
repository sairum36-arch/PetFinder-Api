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

@Mapper(componentModel = "spring", uses = {PetMapper.class})
public interface UserMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "fullName")
    @Mapping(source = "user.credential.email", target = "email")
    @Mapping(source = "token", target = "token")
    AuthResponse toAuthResponse(User user, String token);
    @Mapping(target = "pets", ignore = true)
    @Mapping(target = "email", source = "user.credential.email")
    @Mapping(target = "activeCollarsCount", expression = "java(user.getPets() != null ? user.getPets().size() : 0)")
    UserProfileResponse toBaseProfileDto(User user);
    default UserProfileResponse toFullProfile(User user, PetService petService) {
        if (user == null) {
            return null;
        }
        UserProfileResponse dto = toBaseProfileDto(user);
        //todo don't use service in mapper
        List<PetWithCollarsStatus> petDtos = petService.getPetsForUserProfile(user);
        dto.setPets(petDtos);
        if (dto.getEmail() == null && user.getCredential() != null) {
            dto.setEmail(user.getCredential().getEmail());
        }
        return dto;
    }
}