package com.PetFinder.PetFinder.dto.IncidentDTOS;

import com.PetFinder.PetFinder.dto.BreedDto;
import lombok.Data;

@Data
public class PetInfoDto {
    private Long id;
    private String name;
    private String description;
    private BreedDto breed;
    //TODO сделать с фото логику
}
