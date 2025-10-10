package com.PetFinder.PetFinder.service;
import com.PetFinder.PetFinder.dto.locationHistory.LocationPoint;
import com.PetFinder.PetFinder.entity.CollarEntity;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.LocationHistoryEntity;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.LocationHistoryMapper;
import com.PetFinder.PetFinder.repositories.LocationHistoryRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationHistoryService {

    private final PetRepository petRepository;
    private final LocationHistoryRepository locationHistoryRepository;
    private final LocationHistoryMapper locationHistoryMapper;

    @Transactional(readOnly = true)
    public List<LocationPoint> getHistoryForPet(
            Long petId,
            LocalDateTime start,
            LocalDateTime end,
            CredentialEntity currentUser
    ) {
        PetEntity pet = petRepository.findById(petId)
                .orElseThrow(() -> new EntityNotFoundException("Питомец с id " + petId + " не найден"));
        if (!pet.getUserEntity().getId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("Доступ запрещен");
        }
        CollarEntity collar = pet.getCollarEntity();
        if (collar == null) {
            return Collections.emptyList();
        }
        List<LocationHistoryEntity> history = locationHistoryRepository.findHistoryForPeriod(collar, start, end);
        return locationHistoryMapper.toDtoList(history);
    }
}