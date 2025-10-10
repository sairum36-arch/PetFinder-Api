package com.PetFinder.PetFinder.dto.auth;

import lombok.Data;

@Data

public class UserRegistrationRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;

}
