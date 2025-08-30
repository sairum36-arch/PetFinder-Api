package com.PetFinder.PetFinder.mapper;


import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;
import com.PetFinder.PetFinder.entity.Credential;
import com.PetFinder.PetFinder.entity.Role;
import com.PetFinder.PetFinder.entity.User;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", uses= PasswordEncoder.class)
public interface UserRegistrationMapper {
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "phoneNumber", target = "phoneNumber")
    @Mapping(source = "email", target = "credential.email")
    @Mapping(source = "password", target = "credential.password", qualifiedByName = "hashPassword")
    User toUser(UserRegistrationRequest request, @Context PasswordEncoder encoder);
    @Named("hashPassword")
    default String hashPassword(String password, @Context PasswordEncoder encoder){
        return encoder.encode(password);
    }
    @AfterMapping
    default void defaultLinks(@MappingTarget User user, UserRegistrationRequest request){
        user.getCredential().setUser(user);
        user.getCredential().setRole(Role.USER);
    }

    Credential toCredential(UserRegistrationRequest request);

}
