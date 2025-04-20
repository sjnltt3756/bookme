package com.bookme.service;

import com.bookme.entity.User;
import com.bookme.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isUsernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void registerUser(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);
    }
}