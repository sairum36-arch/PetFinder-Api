package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.mainProfileDTOS.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.petDTOS.PetCreateRequest;
import com.PetFinder.PetFinder.dto.petDTOS.PetDetailResponse;
import com.PetFinder.PetFinder.dto.petDTOS.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.Breed;
import com.PetFinder.PetFinder.entity.Pet;
import com.PetFinder.PetFinder.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BreedMapper.class, CollarMapper.class})
public interface PetMapper {
    Pet toEntity(PetWithCollarsStatus petInfo);
    @Mapping(target = "petMainPhotoUrl", ignore= true)
    PetWithCollarsStatus toDto(Pet pet);
    @Mapping(target = "petMainPhotoUrl", ignore = true)
    PetDetailResponse toDtoDetail(Pet pet);
    List<PetWithCollarsStatus> toDtoList(List<Pet> pets);
    @Mapping(target = "breed", ignore = true)
    @Mapping(target = "collar", ignore = true)
    @Mapping(target = "geoFences", ignore = true)
    @Mapping(target = "mediaFiles", ignore = true)
    Pet toPetBase(PetCreateRequest dto);
    default Pet toPetFull(PetCreateRequest dto, User owner, Breed breed){
        Pet newPet = toPetBase(dto);
        newPet.setUser(owner);
        newPet.setBreed(breed);
        return newPet;
    }
    default void updatePetFromDto(PetUpdateRequest dto, @MappingTarget Pet pet) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null) {
            pet.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            pet.setDescription(dto.getDescription());
        }
        if (dto.getWeight() != null) {
            pet.setWeight(dto.getWeight());
        }
        if (dto.getAge() != null) {
            pet.setAge(dto.getAge());
        }
    }

}
