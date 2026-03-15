package com.app.backend.user.service;

import com.app.backend.security.JWTService;
import com.app.backend.user.entity.User;
import com.app.backend.user.exceptions.InvalidCredentialsException;
import com.app.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JWTService jwtService;

    @InjectMocks
    UserService userService;

    @Test //Test registering a fake user
    void registerUser_WithValidEmail_ShouldSaveUser(){
        String testEmail = "testEmail@email.com";
        String testPassword = "testPassword";
        String encodedPassword = "encodedPassword";

        //Mocks
        when(userRepository.existsByEmail(testEmail)).thenReturn(false);
        when(passwordEncoder.encode(testPassword)).thenReturn(encodedPassword);

        userService.registerUser(testEmail, testPassword);

        verify(userRepository,times(1)).save(any(User.class));

        ArgumentCaptor<User> userCaptor =  ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(testEmail,savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
    }

    @Test
    void test_loginWithValidEmailandPassword_ShouldLogin(){
        String testEmail = "testEmail";
        String testPassword = "testPassword";
        String encodedPassword = "encodedPassword";
        User user = new User(testEmail,encodedPassword);


        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(testPassword,encodedPassword)).thenReturn(true);
        when(jwtService.generateToken(user.getId())).thenReturn("token");

        String result = userService.loginUser(testEmail, testPassword);
        verify(userRepository,times(1)).findByEmail(testEmail);

        assertEquals("token",result);

    }

    @Test
    void test_loginWithValidEmailWrongPassword_ShouldNotLogin(){
        String testEmail = "testEmail";
        String testPassword = "testPassword";
        String encodedPassword = "encodedPassword";
        User user = new User(testEmail,encodedPassword);

        when(userRepository.findByEmail(testEmail)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(testPassword,encodedPassword)).thenReturn(false);

        assertThrows(InvalidCredentialsException.class,() -> userService.loginUser(testEmail, testPassword));

        verify(userRepository,times(1)).findByEmail(testEmail);

    }

}
