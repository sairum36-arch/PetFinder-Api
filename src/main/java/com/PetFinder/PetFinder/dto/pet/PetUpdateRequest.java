package com.PetFinder.PetFinder.dto.pet;

import lombok.Data;

@Data
public class PetUpdateRequest {
    private String name;
    private String description;
    private Long weight;
    private Long age;
    private String mainPhotoKey;
}
