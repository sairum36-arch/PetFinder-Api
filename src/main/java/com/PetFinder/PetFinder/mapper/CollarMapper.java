package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Collar;
import com.PetFinder.PetFinder.entity.CollarEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollarMapper {
    Collar toDto(CollarEntity collarEntity);
}
