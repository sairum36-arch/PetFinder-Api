package com.PetFinder.PetFinder.dto.pet;

import com.PetFinder.PetFinder.dto.Breed;
import com.PetFinder.PetFinder.dto.Collar;
import lombok.Data;

@Data
public class PetDetailResponse {
    private Long id;
    private String name;
    private Breed breed;
    private Long age;
    private Long weight;
    private String description;
    private Collar collar;
    private String petMainPhotoUrl;
}
