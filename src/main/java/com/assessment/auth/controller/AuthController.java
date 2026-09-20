package com.assessment.auth.controller;

import com.assessment.auth.controller.dto.AuthResponse;
import com.assessment.auth.controller.dto.LoginRequest;
import com.assessment.auth.controller.dto.RegisterRequest;
import com.assessment.auth.controller.dto.UserInfoResponse;
import com.assessment.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Endpoints de autenticacion")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("[REQUEST] POST /api/auth/login — usuario: {}", request.username());
        AuthResponse response = authService.login(request);
        log.info("[RESPONSE] POST /api/auth/login — usuario: {}, status: 200", request.username());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        log.info("[REQUEST] POST /api/auth/register — usuario: {}", request.username());
        authService.register(request);
        log.info("[RESPONSE] POST /api/auth/register — usuario: {}, status: 201", request.username());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener informacion del usuario autenticado")
    public ResponseEntity<UserInfoResponse> me(Authentication authentication) {
        log.info("[REQUEST] GET /api/auth/me — usuario: {}", authentication.getName());
        return ResponseEntity.ok(authService.getCurrentUser(authentication.getName()));
    }
}
