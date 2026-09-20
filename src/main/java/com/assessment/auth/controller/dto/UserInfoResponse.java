package com.assessment.auth.controller.dto;

import java.util.List;

public record UserInfoResponse(
    Long id,
    String username,
    String email,
    String nombreCompleto,
    List<String> roles
) {}
