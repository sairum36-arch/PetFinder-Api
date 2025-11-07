package com.PetFinder.PetFinder.service;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.PetEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.repositories.PetRepository;
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
import static org.instancio.Instancio.of;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class PetServiceTest {
    @Mock
    private PetRepository petRepository;
    @InjectMocks
    private PetService petService;

    @Test
    public void deletePetWhenUserIsOwner() {
        long ownerId = 1L;
        long petId = 42L;
        UserEntity owner = of(UserEntity.class).set(field("id"), ownerId).create();
        CredentialEntity currentUser = new CredentialEntity();
        currentUser.setUserEntity(owner);
        currentUser.setUserId(ownerId);
        PetEntity petToDelete = new PetEntity();
        petToDelete.setUserEntity(owner);

        when(petRepository.findById(petId)).thenReturn(Optional.of(petToDelete));
        petService.deletePet(petId, currentUser);
        verify(petRepository, times(1)).delete(petToDelete);
    }

    @Test
    public void deletePetShouldThrowExceptionWhenNotOwner() {
        long ownerId = 1L;
        long anotherUserId = 2L;
        long petId = 42L;
        UserEntity owner = of(UserEntity.class).set(field("id"), ownerId).create();
        PetEntity petToDelete = new PetEntity();
        petToDelete.setUserEntity(owner);
        UserEntity anotherUser = of(UserEntity.class).set(field("id"), anotherUserId).create();
        CredentialEntity currentUser = new CredentialEntity();
        currentUser.setUserEntity(anotherUser);
        currentUser.setUserId(anotherUserId);

        when(petRepository.findById(petId)).thenReturn(Optional.of(petToDelete));
        assertThrows(AccessDeniedException.class, () -> {
            petService.deletePet(petId, currentUser);
        });
        verify(petRepository, never()).delete(any());
    }

}
