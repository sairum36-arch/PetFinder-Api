package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.Incident;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {PetMapper.class, UserMapper.class})
public interface IncidentMapper {
    @Mapping(source = "pet", target = "petInfo")
    @Mapping(source = "pet.user", target = "ownerInfo")
    IncidentDetailResponse toDetailResponse(Incident incident);
    @Mapping(target = "pet", ignore = true)
    Incident toIncidentBase(IncidentCreateRequest dto);
    default Incident toIncidentFull(IncidentCreateRequest dto, Pet pet){
        Incident newIncident = toIncidentBase(dto);
        newIncident.setPet(pet);
        newIncident.setStatus(IncidentStatus.ACTIVE);
        newIncident.setStartedAt(LocalDateTime.now());
        newIncident.setLastKnownLocation(pet.getCollar().getLastLocation());
        return newIncident;
    }
    @Mapping(source = "pet", target = "pet")
    IncidentBriefResponse toBriefResponse(Incident incident);
    List<IncidentBriefResponse> toBriefResponseList(List<Incident> incidents);

}
