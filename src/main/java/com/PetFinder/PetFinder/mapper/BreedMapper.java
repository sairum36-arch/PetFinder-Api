package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.BreedDto;
import com.PetFinder.PetFinder.entity.Breed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    BreedDto toDto(Breed breed);
}
