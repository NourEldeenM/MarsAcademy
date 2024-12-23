package com.academy.mars.service;

import com.academy.mars.config.JwtUtils;
import com.academy.mars.dto.UserDto;
import com.academy.mars.entity.User;
import com.academy.mars.exceptions.BadRequestException;
import com.academy.mars.exceptions.NotFoundException;
import com.academy.mars.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final StudentService studentService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtService = new JwtUtils();
    private final InstructorService instructorService;
    private final AdminService adminService;

    public Map<String, Object> registerNewUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("A user with email " + user.getEmail() + " already exists");
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());

        switch (newUser.getRole().name()) {
            case "ROLE_STUDENT" -> studentService.registerStudent(newUser);
            case "ROLE_INSTRUCTOR" -> instructorService.registerInstructor(newUser);
            case "ROLE_ADMIN" -> adminService.registerAdmin(newUser);
            default -> throw new BadRequestException("Invalid role provided");
        }

        userRepository.save(newUser);
        Long userId = newUser.getId(); // get the auto-generated ID
        Map<String, Object> response = new HashMap<>();
        response.put("user", convertToDto(newUser));
        response.put("id", userId);

        return response;
    }


    public Map<String, Object> login(@RequestBody User user) {
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser == null) {
            throw new NotFoundException("User not found");
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }
        Map<String, Object> response = new HashMap<>();
        response.put("user", convertToDto(foundUser));
        String token = jwtService.generateToken(foundUser);
        response.put("token", token);
        return response;
    }

    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
