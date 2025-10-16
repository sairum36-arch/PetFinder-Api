package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Incident.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.Incident.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.IncidentMapper;
import com.PetFinder.PetFinder.repositories.IncidentParticipationRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final PetRepository petRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final IncidentParticipationRepository participationRepository;

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
    @Transactional(readOnly = true)
    public List<IncidentBriefResponse> findAllActiveIncidents(){
        return incidentRepository.findAllByStatus(IncidentStatus.ACTIVE).stream().map(incidentMapper::toBriefResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IncidentDetailResponse getIncidentDetails(Long incidentId){
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("инцидент с id: " + incidentId + " не найден"));
        return incidentMapper.toDetailResponse(incident);
    }


    @Transactional
    public void respondToIncident(Long incidentId, CredentialEntity currentUser) {
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("инцидент с id " + "incidentId" + " не найден"));
        UserEntity helper = currentUser.getUserEntity();
        if (incident.getPetEntity().getUserEntity().getId().equals(helper.getId())) {
            throw new IllegalStateException("Вы не можете откликнуться на собственный инцидент.");
        }
        boolean alreadyResponded = participationRepository.existsByIncidentEntityAndHelperUserEntity(incident, helper);
        if (alreadyResponded) {
            throw new IllegalStateException("Вы уже откликнулись на этот инцидент.");
        }
        IncidentParticipationEntity participation = new IncidentParticipationEntity();
        participation.setIncidentEntity(incident);
        participation.setHelperUserEntity(helper);
        participation.setRespondedAt(LocalDateTime.now());
        participationRepository.save(participation);
    }
    @Transactional
    public void closeIncidentByOwner(Long incidentId, CredentialEntity currentUser){
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("Инцидент с id " + incidentId + " не найден"));
        Long ownerId = incident.getPetEntity().getUserEntity().getId();
        Long currentUserId = currentUser.getUserId();

        if (!ownerId.equals(currentUserId)) {
            throw new AccessDeniedException("Вы не можете закрыть инцидент, так как не являетесь владельцем питомца.");
        }
        if (incident.getStatus() != IncidentStatus.ACTIVE) {
            throw new IllegalStateException("Этот инцидент уже закрыт. Текущий статус: " + incident.getStatus());
        }
        incident.setStatus(IncidentStatus.CLOSED_BY_OWNER);
    }
}
