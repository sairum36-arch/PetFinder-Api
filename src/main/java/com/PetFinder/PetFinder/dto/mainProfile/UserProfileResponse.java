package com.PetFinder.PetFinder.dto.mainProfile;

import lombok.Data;

import java.util.List;

@Data

public class UserProfileResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
    private String avatarUrl;
    private int activeCollarsCount;
    private List<PetWithCollarsStatus> pets;
    private int unreadNotificationsCount;
}
