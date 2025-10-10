package com.PetFinder.PetFinder.dto.auth;

import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private Long userId;
    private String email;
    private String name;
}
