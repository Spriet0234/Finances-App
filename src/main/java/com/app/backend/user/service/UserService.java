package com.app.backend.user.service;

import com.app.backend.user.entity.User;
import com.app.backend.user.exceptions.EmailAlreadyExistsException;
import com.app.backend.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository
                .findById(UUID.fromString(userId))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void registerUser(String email, String password){
        validateEmailUniqueness(email); //Will throw exception if email already exists
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword);
        userRepository.save(user);
    }

    void validateEmailUniqueness(String email){

        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }




}
