package com.academy.mars.controller;

import com.academy.mars.config.JwtUtils;
import com.academy.mars.entity.User;
import com.academy.mars.entity.UserRole;
import com.academy.mars.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authService;

    @Mock
    private JwtUtils jwtService;

    @InjectMocks
    private AuthenticationController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignup() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(UserRole.ROLE_STUDENT);

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("id", 1L);
        mockResponse.put("user", user);

        when(authService.registerNewUser(user)).thenReturn(mockResponse);
        when(jwtService.generateToken(user)).thenReturn("mocked-jwt-token");

        ResponseEntity<?> responseEntity = authController.signup(user);

        assertEquals(201, responseEntity.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assert responseBody != null;
        assertEquals("mocked-jwt-token", responseBody.get("token"));
        verify(authService, times(1)).registerNewUser(user);
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    void testLogin() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("user", user);
        mockResponse.put("token", "mocked-jwt-token");

        when(authService.login(user)).thenReturn(mockResponse);
        ResponseEntity<?> responseEntity = authController.login(user);

        assertEquals(200, responseEntity.getStatusCodeValue());
        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();
        assert responseBody != null;
        assertEquals("mocked-jwt-token", responseBody.get("token"));
        verify(authService, times(1)).login(user);
    }
}
