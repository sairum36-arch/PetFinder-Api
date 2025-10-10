package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Breed;
import com.PetFinder.PetFinder.entity.BreedEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BreedMapper {
    Breed toDto(BreedEntity breedEntity);
}
