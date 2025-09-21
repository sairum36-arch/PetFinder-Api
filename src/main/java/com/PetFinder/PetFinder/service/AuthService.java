package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.auth.AuthResponse;
import com.PetFinder.PetFinder.dto.auth.LoginRequest;
import com.PetFinder.PetFinder.dto.auth.UserRegistrationRequest;


import com.PetFinder.PetFinder.entity.User;
import com.PetFinder.PetFinder.exception.EmailAlreadyExistsException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.PetFinder.PetFinder.mapper.UserMapper;
import com.PetFinder.PetFinder.mapper.UserRegistrationMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.PetFinder.PetFinder.repositories.CredentialRepository;
import com.PetFinder.PetFinder.repositories.UserRepository;
import com.PetFinder.PetFinder.securityConfig.JwtTokenProvider;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final UserRegistrationMapper userRegistrationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final UserMapper userMapper;
    @Transactional
    public void register(UserRegistrationRequest request) {
        if (credentialRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Пользователь с таким email уже существует");
        }
        User user = userRegistrationMapper.toUser(request, passwordEncoder);
        userRepository.save(user);
    }
    public AuthResponse login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Неверный email или пароль", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = tokenProvider.generateToken(userDetails);
        User user = userRepository.findByCredentialEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден после успешной аутентификации. Это аномалия."));
        return userMapper.toAuthResponse(user, token);
    }
}