package mapper;

import dto.mainProfileDTOS.PetWithCollarsStatus;
import entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {
    Pet toEntity(PetWithCollarsStatus petInfo);
    @Mapping(target = "petMainPhotoUrl", ignore= true)
    PetWithCollarsStatus toDto(Pet pet);
    List<PetWithCollarsStatus> toDtoList(List<Pet> pets);
}
