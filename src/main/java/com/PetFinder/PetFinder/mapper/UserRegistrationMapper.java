package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.Role;
import com.PetFinder.PetFinder.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    default UserEntity toUser(UserRegistrationRequest request, @Context PasswordEncoder passwordEncoder) {
        if (request == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setPhoneNumber(request.getPhoneNumber());
        CredentialEntity credentialEntity = new CredentialEntity();
        credentialEntity.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            credentialEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        credentialEntity.setRole(Role.USER);
        userEntity.setCredentialEntity(credentialEntity);
        credentialEntity.setUserEntity(userEntity);
        return userEntity;
    }
}