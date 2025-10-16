package com.PetFinder.PetFinder.service;


import com.PetFinder.PetFinder.dto.mainProfile.PetWithCollarsStatus;
import com.PetFinder.PetFinder.dto.mainProfile.UserProfileResponse;
import com.PetFinder.PetFinder.dto.mainProfile.UserUpdate;
import com.PetFinder.PetFinder.entity.*;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.PetMapper;
import com.PetFinder.PetFinder.repositories.NotificationRepository;
import com.PetFinder.PetFinder.repositories.PetRepository;
import com.PetFinder.PetFinder.service.FileStoragePackage.FileStorageService;
import lombok.RequiredArgsConstructor;
import com.PetFinder.PetFinder.mapper.UserMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.PetFinder.PetFinder.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final NotificationRepository notificationRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getMyProfile(String email) {
        UserEntity userEntity = userRepository.findUserDetailsByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с email: " + email + " не найден"));
        if (userEntity.getPetEntities() != null && !userEntity.getPetEntities().isEmpty()) {
            petRepository.findWithMedia(userEntity.getPetEntities());
        }

        UserProfileResponse profileResponse = userMapper.toUserProfileResponse(userEntity);
        long unreadCount = notificationRepository.countUnreadNotificationsForUser(userEntity);
        profileResponse.setUnreadNotificationsCount((int) unreadCount);
        return profileResponse;
    }

    @Transactional
    public UserProfileResponse updateUserAvatar(String email, String fileKey){
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("пользователь с email: " + email + " не найден"));
        userEntity.setAvatarKey(fileKey);
        return getMyProfile(email);
    }

    @Transactional
    public UserProfileResponse updateUserProfile(String email, UserUpdate userUpdate){
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Пользователь с email: " + email + " не найден"));
        if(userUpdate.getName() != null){
            userEntity.setName(userUpdate.getName());
        }
        if(userUpdate.getPhoneNumber() != null){
            userEntity.setPhoneNumber(userUpdate.getPhoneNumber());
        }
        return getMyProfile(email);
    }


}