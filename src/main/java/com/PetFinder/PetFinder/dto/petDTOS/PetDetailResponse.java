package com.PetFinder.PetFinder.dto.petDTOS;

import com.PetFinder.PetFinder.dto.BreedDto;
import com.PetFinder.PetFinder.dto.CollarDto;
import com.PetFinder.PetFinder.entity.Breed;
import com.PetFinder.PetFinder.entity.Collar;
import lombok.Data;

@Data
public class PetDetailResponse {
    private Long id;
    private String name;
    private BreedDto breed;
    private Long age;
    private Long weight;
    private String description;
    private CollarDto collar;
}
