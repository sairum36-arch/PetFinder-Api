package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PetFinder.PetFinder.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public UserProfileResponse mainProfileInfo(@AuthenticationPrincipal UserDetails currentUser) {
        return userService.getMyProfile(currentUser.getUsername());
    }
}
