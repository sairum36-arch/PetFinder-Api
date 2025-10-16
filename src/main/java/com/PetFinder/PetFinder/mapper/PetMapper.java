package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.CoordinateDto;
import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.pet.PetCreateRequest;
import com.PetFinder.PetFinder.dto.pet.PetDetailResponse;
import com.PetFinder.PetFinder.dto.pet.PetUpdateRequest;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.service.FileStoragePackage.UrlBuilderService;
import org.locationtech.jts.geom.Point;
import org.mapstruct.*;


import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BreedMapper.class, CollarMapper.class, UrlBuilderService.class})
public interface PetMapper {
    PetEntity toEntity(PetWithCollarsStatus petInfo);
    @Mapping(source = "id", target = "petId")
    @Mapping(source = "name", target = "petName")
    @Mapping(source = "collarEntity.id", target = "collarId")
    @Mapping(source = "collarEntity.status", target = "collarStatus")
    @Mapping(source = "collarEntity.batteryLevel", target = "batteryLevel")
    @Mapping(source = "collarEntity.lastLocation", target = "lastKnownLocation")
    @Mapping(source = ".", target = "petMainPhotoUrl")
    PetWithCollarsStatus toDto(PetEntity petEntity);

    @Mapping(source = ".", target = "petMainPhotoUrl")
    PetDetailResponse toDtoDetail(PetEntity petEntity);


    List<PetWithCollarsStatus> toDtoList(List<PetEntity> petEntities);

    @Mapping(target = "breedEntity", ignore = true)
    @Mapping(target = "collarEntity", ignore = true)
    @Mapping(target = "geoFenceEntities", ignore = true)
    @Mapping(target = "mediaFiles", ignore = true)
    PetEntity toPetBase(PetCreateRequest dto);
    default CoordinateDto pointToCoordinateDto(Point point){
        if(point == null){
            return null;
        }
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setLongitude(point.getX());
        coordinateDto.setLatitude(point.getY());
        return coordinateDto;
    }



    default void updatePetFromDto(PetUpdateRequest dto, @MappingTarget PetEntity petEntity) {
        if (dto == null) {
            return;
        }
        if (dto.getName() != null) {
            petEntity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            petEntity.setDescription(dto.getDescription());
        }
        if (dto.getWeight() != null) {
            petEntity.setWeight(dto.getWeight());
        }
        if (dto.getAge() != null) {
            petEntity.setAge(dto.getAge());
        }
    }


}
