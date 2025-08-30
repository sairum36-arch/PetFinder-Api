package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.auth.AuthResponse;
import com.PetFinder.PetFinder.dto.auth.LoginRequest;
import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PetFinder.PetFinder.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegistrationRequest request){
        authService.register(request);

    }
    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest request){
        return authService.login(request);

    }
}
