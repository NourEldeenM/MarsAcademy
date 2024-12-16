package com.academy.mars.Security.Authentication;

import com.academy.mars.Security.DTO.LoginRequest;
import com.academy.mars.Security.DTO.LoginResponse;
import com.academy.mars.Security.DTO.SignupRequest;
import com.academy.mars.UserManagement.User;
import com.academy.mars.UserManagement.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public String signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getRole());

        userRepository.save(user);
        return "User registered successfully!";
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = "dummy-access-token"; // Replace with real token generation
        String refreshToken = "dummy-refresh-token";

        return new LoginResponse(accessToken, refreshToken);
    }
}
