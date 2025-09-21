package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;
import com.PetFinder.PetFinder.entity.Credential;
import com.PetFinder.PetFinder.entity.Role;
import com.PetFinder.PetFinder.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper {
    default User toUser(UserRegistrationRequest request, @Context PasswordEncoder passwordEncoder) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        Credential credential = new Credential();
        credential.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            credential.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        credential.setRole(Role.USER);
        user.setCredential(credential);
        credential.setUser(user);
        return user;
    }
}