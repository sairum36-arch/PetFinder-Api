package com.PetFinder.PetFinder.dto.petDTOS;

import lombok.Data;

@Data
public class PetCreateRequest {
    private String name;
    private Long breedId;
    private Long age;
    private Long weight;
    private String description;
}
