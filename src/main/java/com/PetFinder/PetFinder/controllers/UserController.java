package com.PetFinder.PetFinder.controllers;

import com.PetFinder.PetFinder.dto.mainProfile.AvatarUpdateRequest;
import com.PetFinder.PetFinder.dto.mainProfile.UserProfileResponse;
import com.PetFinder.PetFinder.dto.mainProfile.UserUpdate;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/me")
    public UserProfileResponse updateProfile(@AuthenticationPrincipal UserDetails currentUser, @RequestBody UserUpdate userUpdate){
        return userService.updateUserProfile(currentUser.getUsername(),userUpdate);
    }

    @PutMapping("/me/avatar")
    public UserProfileResponse updateUserAvatar(@AuthenticationPrincipal CredentialEntity currentUser, @RequestBody AvatarUpdateRequest request){
        return userService.updateUserAvatar(currentUser.getUsername(), request.getFileKey());
    }
}
