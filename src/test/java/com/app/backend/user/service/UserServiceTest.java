package com.app.backend.user.service;

import com.app.backend.security.JWTService;
import com.app.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JWTService jwtService;

}
