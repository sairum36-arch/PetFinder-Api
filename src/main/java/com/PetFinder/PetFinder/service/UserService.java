package com.PetFinder.PetFinder.service;


import com.PetFinder.PetFinder.dto.IncidentDTOS.IncidentCloseRequest;
import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.repositories.CollarRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import com.PetFinder.PetFinder.securityConfig.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.mapper.UserMapper;
import org.springframework.stereotype.Service;
import com.PetFinder.PetFinder.repositories.UserRepository;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final PetService petService;
    private final IncidentRepository incidentRepository;
    private final CollarRepository collarRepository;

    public UserProfileResponse getMyProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден"));
        return userMapper.toFullProfile(user, petService);
    }


}
