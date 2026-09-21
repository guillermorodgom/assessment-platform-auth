package com.assessment.auth.controller;

import com.assessment.auth.controller.dto.AuthResponse;
import com.assessment.auth.controller.dto.LoginRequest;
import com.assessment.auth.controller.dto.RegisterRequest;
import com.assessment.auth.controller.dto.UserInfoResponse;
import com.assessment.auth.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock private AuthService authService;
    @Mock private Authentication authentication;

    @InjectMocks private AuthController authController;

    @Test
    void login_returnsOkWithToken() {
        LoginRequest request = new LoginRequest("admin", "admin123");
        AuthResponse authResponse = new AuthResponse(
                "jwt-token", "admin", "admin@mail.com", "Admin User", List.of("ROLE_ADMIN"));
        when(authService.login(request)).thenReturn(authResponse);

        ResponseEntity<AuthResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwt-token", response.getBody().token());
        assertEquals("admin", response.getBody().username());
    }

    @Test
    void register_returns201() {
        RegisterRequest request = new RegisterRequest("newuser", "pass123", "new@mail.com", "New User");

        ResponseEntity<Void> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authService).register(request);
    }

    @Test
    void me_returnsUserInfo() {
        when(authentication.getName()).thenReturn("candidato1");
        UserInfoResponse info = new UserInfoResponse(
                1L, "candidato1", "c@mail.com", "Candidato Uno", List.of("ROLE_CANDIDATO"));
        when(authService.getCurrentUser("candidato1")).thenReturn(info);

        ResponseEntity<UserInfoResponse> response = authController.me(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("candidato1", response.getBody().username());
        assertTrue(response.getBody().roles().contains("ROLE_CANDIDATO"));
    }
}
