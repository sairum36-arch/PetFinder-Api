package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.Incident.IncidentCreateRequest;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.mapper.IncidentMapper;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class IncidentServiceTest {

    @Mock
    private PetRepository petRepository;
    @Mock
    private IncidentRepository incidentRepository;
    @Mock
    private CollarDataProcessService collarDataProcessService;
    @Mock
    private IncidentMapper incidentMapper;

    @InjectMocks
    private IncidentService incidentService;

    @Test
    public void createIncidentWhenUserIsOwnerAndNoActiveIncident(){
        long expectedOwnerId = 123L;
        UserEntity owner = Instancio.of(UserEntity.class)
                .set(field("id"), expectedOwnerId)
                .create();
        CredentialEntity currentUser = new CredentialEntity();
        currentUser.setUserEntity(owner);
        currentUser.setUserId(expectedOwnerId);
        PetEntity pet = new PetEntity();
        pet.setUserEntity(owner);
        pet.setCollarEntity(new CollarEntity());
        IncidentCreateRequest request = new IncidentCreateRequest();
        request.setPetId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(incidentRepository.existsByPetAndStatus(pet, IncidentStatus.ACTIVE)).thenReturn(false);
        when(incidentMapper.toEntity(any(IncidentCreateRequest.class))).thenReturn(new IncidentEntity());
        incidentService.createIncident(currentUser, request);
        verify(incidentRepository, times(1)).save(any(IncidentEntity.class));
        verify(collarDataProcessService, times(1)).sendCollarStatusUpdate(any(CollarEntity.class));
    }

    @Test
    public void createIncidentWhenUserIsNotOwner(){
        UserEntity owner = Instancio.create(UserEntity.class);
        UserEntity anotherUser = Instancio.create(UserEntity.class);
        CredentialEntity currentUser = new CredentialEntity();
        currentUser.setUserEntity(anotherUser);
        PetEntity pet = new PetEntity();
        pet.setUserEntity(owner);
        IncidentCreateRequest request = new IncidentCreateRequest();
        request.setPetId(1L);

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        assertThrows(AccessDeniedException.class, () -> {
            incidentService.createIncident(currentUser, request);
        });
        verify(incidentRepository, never()).save(any());
    }
}