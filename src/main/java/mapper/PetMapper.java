package mapper;

import dto.mainProfileDTOS.PetWithCollarsStatus;
import entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet toPetWithCollarStatusDto(PetWithCollarsStatus petInfo);
    @Mapping(target = "petMainPhotoUrl", ignore= true)
    PetWithCollarsStatus toPetWithCollarStatusEntity(Pet pet);
    List<PetWithCollarsStatus> toPetWithCollarStatusEntityList(List<Pet> pets);
}
