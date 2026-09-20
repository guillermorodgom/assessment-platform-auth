package com.assessment.auth.controller.dto;

import java.util.List;

public record AuthResponse(
    String token,
    String username,
    String email,
    String nombreCompleto,
    List<String> roles
) {}
