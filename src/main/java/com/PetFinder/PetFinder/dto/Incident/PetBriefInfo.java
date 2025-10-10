package com.PetFinder.PetFinder.dto.Incident;

import lombok.Data;

@Data
public class PetBriefInfo {
    private Long id;
    private String name;
    private String mainPhotoUrl;
    private String breedName;
}