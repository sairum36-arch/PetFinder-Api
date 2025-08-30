package com.PetFinder.PetFinder.dto.mainProfileDTOS;

import lombok.Data;

import java.util.List;

@Data

public class UserProfileResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String avatarUrl;
    private int activeCollarsCount;
    private List<PetWithCollarsStatus> pets;

}
