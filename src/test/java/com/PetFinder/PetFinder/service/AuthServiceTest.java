package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.exception.EmailAlreadyExistsException;
import com.PetFinder.PetFinder.mapper.UserRegistrationMapper;
import com.PetFinder.PetFinder.repositories.CredentialRepository;
import com.PetFinder.PetFinder.repositories.UserRepository;
import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private CredentialRepository credentialRepository;
    @Mock
    private UserRegistrationMapper userRegistrationMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthService authService;

    @Test
    public void registerShouldSaveUser(){
        UserRegistrationRequest request = Instancio.create(UserRegistrationRequest.class);
        when(credentialRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRegistrationMapper.toUser(any(UserRegistrationRequest.class), any(PasswordEncoder.class)))
                .thenReturn(new UserEntity());
        authService.register(request);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void registerShouldThrowExceptionWhenEmailExist(){
        UserRegistrationRequest request = Instancio.create(UserRegistrationRequest.class);
        when(credentialRepository.existsByEmail(request.getEmail())).thenReturn(true);
        assertThrows(EmailAlreadyExistsException.class, () -> {
            authService.register(request);});
        verify(userRepository, never()).save(any());
    }
}