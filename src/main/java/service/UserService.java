package service;


import dto.mainProfileDTOS.GeoFenceDTO;
import dto.mainProfileDTOS.PetWithCollarsStatus;
import dto.mainProfileDTOS.UserProfileResponse;
import entity.Collar;
import entity.GeoFence;
import entity.Pet;
import entity.User;
import exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.GeoFenceMapper;
import mapper.PetMapper;
import mapper.UserMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final GeoFenceMapper geofenceMapper;

    public UserProfileResponse getMainProfileInfoByUserId(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("пользователь не найден с id:" + userId));
       return userMapper.toUserProfileResponse(user);
    }
    public  UserProfileResponse getMainProfileByPrincipal(Principal principal){
        String userEmail = principal.getName();
        User user = userRepository.findByCredentialEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("Пользователь с таким email не найден"));
        return this.getMainProfileInfoByUserId(user.getId());
    }


}
