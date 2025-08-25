package controllers;

import dto.mainProfileDTOS.UserProfileResponse;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/me")
    public UserProfileResponse mainProfileInfo(Principal principal){
        return userService.getMainProfileByPrincipal(principal);
    }
}
