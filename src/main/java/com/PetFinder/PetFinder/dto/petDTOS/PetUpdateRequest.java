package com.PetFinder.PetFinder.dto.petDTOS;

import lombok.Data;

@Data
public class PetUpdateRequest {
    private String name;
    private String description;
    private Long weight;
    private Long age;
}
