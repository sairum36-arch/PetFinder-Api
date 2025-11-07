package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Incident.IncidentBriefResponse;
import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.dto.Incident.IncidentDetailResponse;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.IncidentMapper;
import com.PetFinder.PetFinder.mapper.NotificationMapper;
import com.PetFinder.PetFinder.repositories.IncidentParticipationRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncidentService {
    private final PetRepository petRepository;
    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;
    private final IncidentParticipationRepository participationRepository;
    private final CollarDataProcessService collarDataProcessService;
    private final NotificationService notificationService;
    private static final Logger log = LoggerFactory.getLogger(IncidentService.class);
    public IncidentBriefResponse createIncident(CredentialEntity currentUser, IncidentCreateRequest dto){
        PetEntity petIncident = petRepository.findById(dto.getPetId()).orElseThrow(() -> new EntityNotFoundException("Питомец с id " + dto.getPetId() + " не найден"));
        if(!petIncident.getUserEntity().getId().equals(currentUser.getUserId())){
            throw new AccessDeniedException("Доступ запрещен к чужому питомцу");
        }
        boolean isAlreadyActive = incidentRepository.existsByPetAndStatus(petIncident, IncidentStatus.ACTIVE);
        if(isAlreadyActive){
            throw new IllegalStateException("Для этого питомца уже активирован режим поиска");
        }
        if (petIncident.getCollarEntity() != null) {
            CollarEntity collar = petIncident.getCollarEntity();
            collar.setStatus(CollarStatus.IN_SEARCH_MODE);
            collarDataProcessService.sendCollarStatusUpdate(collar);
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
    public List<IncidentBriefResponse> findAllActiveIncidents(Double userLat, Double userLon) {
        List<IncidentEntity> incidents = incidentRepository.findAllByStatus(IncidentStatus.ACTIVE);
        List<IncidentBriefResponse> dtos = incidents.stream().map(incidentMapper::toBriefResponse)
                .collect(Collectors.toList());
        if (userLat != null && userLon != null) {
            for (IncidentBriefResponse dto : dtos) {
                if (dto.getLastKnownLocation() != null) {
                    double distanceInKm = calculateDistanceInKm(
                            userLat,
                            userLon,
                            dto.getLastKnownLocation().getLatitude(),
                            dto.getLastKnownLocation().getLongitude()
                    );
                    dto.setDistanceToUserKm(distanceInKm);
                }
            }
            Collections.sort(dtos, Comparator.comparing(
                    IncidentBriefResponse::getDistanceToUserKm,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
        }
        return dtos;
    }

    private double calculateDistanceInKm(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }

        double earthRadiusKm = 6371.0;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }

    @Transactional(readOnly = true)
    public IncidentDetailResponse getIncidentDetails(Long incidentId){
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("инцидент с id: " + incidentId + " не найден"));
        return incidentMapper.toDetailResponse(incident);
    }


    @Transactional
    public void respondToIncident(Long incidentId, CredentialEntity currentUser) {
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("инцидент с id " + incidentId + " не найден"));
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
        IncidentEntity incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new EntityNotFoundException("Инцидент с id " + incidentId + " не найден"));
        Long ownerId = incident.getPetEntity().getUserEntity().getId();
        Long currentUserId = currentUser.getUserId();
        if (!ownerId.equals(currentUserId)) {
            throw new AccessDeniedException("Вы не можете закрыть инцидент, так как не являетесь владельцем питомца.");
        }
        if (incident.getStatus() != IncidentStatus.ACTIVE) {
            throw new IllegalStateException("Этот инцидент уже закрыт. Текущий статус: " + incident.getStatus());
        }
        PetEntity pet = incident.getPetEntity();
        if (pet.getCollarEntity() != null) {
            CollarEntity collar = pet.getCollarEntity();
            collar.setStatus(CollarStatus.ACTIVE);
            collarDataProcessService.sendCollarStatusUpdate(collar);
        }
        incident.setStatus(IncidentStatus.CLOSED_BY_OWNER);
    }

    @Transactional
    public void cancelIncident(Long incidentId, CredentialEntity currentUser) {
        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("Инцидент с id " + incidentId + " не найден."));
        if (!incident.getPetEntity().getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Вы не можете отменить инцидент, так как не являетесь владельцем питомца.");
        }

        if (incident.getStatus() != IncidentStatus.ACTIVE) {
            throw new IllegalStateException("Этот инцидент уже не активен и не может быть отменен. Текущий статус: " + incident.getStatus());
        }
        PetEntity pet = incident.getPetEntity();
        if (pet.getCollarEntity() != null) {
            CollarEntity collar = pet.getCollarEntity();
            collar.setStatus(CollarStatus.ACTIVE);
            collarDataProcessService.sendCollarStatusUpdate(collar);
        }
        incident.setStatus(IncidentStatus.CANCELED);
    }
    @Transactional
    public void resolveIncidentByHelper(Long incidentId, CredentialEntity currentUser) {

        IncidentEntity incident = incidentRepository.findById(incidentId).orElseThrow(() -> new EntityNotFoundException("Инцидент с id " + incidentId + " не найден."));
        if (incident.getStatus() != IncidentStatus.ACTIVE) {
            throw new IllegalStateException("Этот инцидент уже не активен. Текущий статус: " + incident.getStatus());
        }
        boolean isHelper = participationRepository.existsByIncidentEntityAndHelperUserEntity(incident, currentUser.getUserEntity());
        if (!isHelper) {
            throw new AccessDeniedException("Вы не можете завершить этот инцидент, так как не являетесь откликнувшимся помощником.");
        }
        PetEntity pet = incident.getPetEntity();
        if (pet.getCollarEntity() != null) {
            CollarEntity collar = pet.getCollarEntity();
            collar.setStatus(CollarStatus.ACTIVE);
            collarDataProcessService.sendCollarStatusUpdate(collar);
        }
        incident.setStatus(IncidentStatus.CLOSED_BY_HELPER);
        incident.setResolvedBy(currentUser.getUserEntity());
        UserEntity owner = pet.getUserEntity();
        String message = String.format("Ваш питомец '%s' был найден пользователем %s! Инцидент завершен.", pet.getName(), currentUser.getUserEntity().getName());
        notificationService.createNotification(owner, NotificationType.NEW_MESSAGE, message);
    }


}
