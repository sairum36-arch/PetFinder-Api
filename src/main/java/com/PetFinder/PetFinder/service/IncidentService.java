package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Incident.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.Incident.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentStatus;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.IncidentMapper;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final PetRepository petRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    public IncidentBriefResponse createIncident(CredentialEntity currentUser, IncidentCreateRequest dto){
        PetEntity petIncident = petRepository.findById(dto.getPetId()).orElseThrow(() -> new EntityNotFoundException("Питомец с id " + dto.getPetId() + " не найден"));
        if(!petIncident.getUserEntity().getId().equals(currentUser.getUserId())){
            throw new AccessDeniedException("Доступ запрещен к чужому питомцу");
        }
        boolean isAlreadyActive = incidentRepository.existsByPetAndStatus(petIncident, IncidentStatus.ACTIVE);
        if(isAlreadyActive){
            throw new IllegalStateException("Для этого питомца уже активирован режим поиска");
        }
        IncidentEntity newIncident = incidentMapper.toEntity(dto);
        newIncident.setPetEntity(petIncident);
        newIncident.setStatus(IncidentStatus.ACTIVE);
        newIncident.setStartedAt(LocalDateTime.now());
        if(petIncident.getCollarEntity() != null){
            newIncident.setLastKnownLocation(petIncident.getCollarEntity().getLastLocation());
        }
        IncidentEntity savedIncident = incidentRepository.save(newIncident);
        return incidentMapper.toBriefResponse(savedIncident);
    }

    public List<IncidentBriefResponse> findAllActiveIncidents(){
        return incidentRepository.findAllByStatus(IncidentStatus.ACTIVE).stream().map(incidentMapper::toBriefResponse).collect(Collectors.toList());
    }
    public IncidentDetailResponse getIncidentDetails(Long incidentId){
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("инцидент с id: " + incidentId + " не найден"));
        return incidentMapper.toDetailResponse(incident);
    }

}
