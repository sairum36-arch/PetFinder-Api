package com.PetFinder.PetFinder.dto.Incident;

import com.PetFinder.PetFinder.dto.Breed;
import lombok.Data;

@Data
public class PetInfo {
    private Long id;
    private String name;
    private String description;
    private Breed breed;
    private String mainPhotoUrl;
}
