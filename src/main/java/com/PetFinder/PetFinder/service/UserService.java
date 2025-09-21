package com.PetFinder.PetFinder.service;


import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.repositories.CollarRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.PetFinder.PetFinder.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PetService petService;
    public UserProfileResponse getMyProfile(String email) {
        User user = userRepository.findByCredentialEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с email: " + email + " не найден"));
        return userMapper.toFullProfile(user, petService);
    }
}