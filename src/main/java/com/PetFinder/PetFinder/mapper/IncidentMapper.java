package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.Incident.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.Incident.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, UserMapper.class})
public interface IncidentMapper {
    @Mapping(target = "ownersNotes", source = "ownerNotes")
    IncidentEntity toEntity(IncidentCreateRequest dto);

    @Mapping(source = "petEntity", target = "petInfo")
    @Mapping(source = "petEntity.userEntity", target = "ownerInfo")
    IncidentDetailResponse toDetailResponse(IncidentEntity entity);

    @Mapping(source = "petEntity", target = "pet")
    @Mapping(target = "distanceToUserKm", ignore = true)
    IncidentBriefResponse toBriefResponse(IncidentEntity entity);

}
