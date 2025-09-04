package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.CollarDto;
import com.PetFinder.PetFinder.entity.Collar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CollarMapper {
    CollarDto toDto(Collar collar);
}
