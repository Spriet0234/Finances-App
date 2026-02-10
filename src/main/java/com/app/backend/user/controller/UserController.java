package com.app.backend.user.controller;

import com.app.backend.user.dto.LoginResponse;
import com.app.backend.user.dto.RegisterRequest;
import com.app.backend.user.entity.User;
import com.app.backend.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/me")
    public String health(@AuthenticationPrincipal User user) {
       return "Hello " + user.getUsername();
    }

    @PostMapping("/auth/register")
    public String register(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        userService.registerUser(email,password);
        return "User registered successfully";
    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        String token = userService.loginUser(email,password);

        return new LoginResponse(token);
    }
}
