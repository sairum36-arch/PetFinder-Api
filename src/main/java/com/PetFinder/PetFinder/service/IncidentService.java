package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.IncidentMapper;
import com.PetFinder.PetFinder.repositories.*;
import com.PetFinder.PetFinder.securityConfig.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final PetRepository petRepository;
    private final CollarRepository collarRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final IncidentResponseRepository incidentResponseRepository;
    private final NotificationRepository notificationRepository;
    public IncidentDetailResponse createIncident(IncidentCreateRequest dto, CustomUserDetails currentUser){
        Pet pet = petRepository.findById(dto.getPetId()).orElseThrow(() -> new EntityNotFoundException("животное с таким id не найдено"));
        if(incidentRepository.existsByPetAndStatus(pet, IncidentStatus.ACTIVE)){
            throw new IllegalStateException("объявление уже существует");
        }
        Incident newIncident = incidentMapper.toIncidentFull(dto, pet);
        Collar collar = pet.getCollar();
        collar.setStatus(CollarStatus.IN_SEARCH_MODE);
        collarRepository.save(collar);
        return incidentMapper.toDetailResponse(incidentRepository.save(newIncident));
    }
    public IncidentDetailResponse getIncidentDetails(Long incidentId) {
        Incident incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("Инцидент с таким id не найден."));
        return incidentMapper.toDetailResponse(incident);
    }
}
