package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.mainProfileDTOS.GeoFenceDTO;
import com.PetFinder.PetFinder.entity.GeoFence;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeoFenceMapper {
    GeoFenceDTO toDTO(GeoFence geoFence);
    GeoFence toEntity(GeoFenceDTO geoFence);
    List<GeoFenceDTO> toDTOList(List<GeoFence> geoFence);
}
