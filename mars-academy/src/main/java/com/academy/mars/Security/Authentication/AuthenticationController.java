package com.academy.mars.Security.Authentication;

import com.academy.mars.Security.Jwt.JwtService;
import com.academy.mars.UserManagement.User;
import com.academy.mars.UserManagement.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationService authService;

//    @Operation(summary = "User Signup", description = "Register a new user")
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
//        try {
//            String response = authService.signup(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @Operation(summary = "User Login", description = "Authenticate user and generate a JWT token")
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//        try {
//            LoginResponse response = authService.login(request);
//            return ResponseEntity.ok(response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserRole(user.getUserRole());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        user = userRepository.save(user);
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (foundUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String token = jwtService.generateToken(user);
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", foundUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
