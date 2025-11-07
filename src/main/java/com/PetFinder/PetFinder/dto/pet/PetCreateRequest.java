package com.PetFinder.PetFinder.dto.pet;

import lombok.Data;

@Data
public class PetCreateRequest {
    private String name;
    private Long breedId;
    private Long age;
    private Long weight;
    private String description;
    private String mainPhotoKey;
    private String deviceId;
}
