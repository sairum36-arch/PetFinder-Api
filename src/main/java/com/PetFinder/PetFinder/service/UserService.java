package com.PetFinder.PetFinder.service;


import com.PetFinder.PetFinder.dto.mainProfileDTOS.UserProfileResponse;
import com.PetFinder.PetFinder.entity.User;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
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
    private final PetMapper petMapper;

    public UserProfileResponse getMyProfile(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден"));
        return userMapper.toUserProfileResponse(user);
    }

}
