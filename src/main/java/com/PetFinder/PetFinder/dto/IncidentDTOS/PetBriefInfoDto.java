package com.PetFinder.PetFinder.dto.IncidentDTOS;

import lombok.Data;

@Data
public class PetBriefInfoDto {
    private Long id;
    private String name;
    private String mainPhotoUrl;
    private String breedName;
}