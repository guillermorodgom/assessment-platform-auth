package com.assessment.auth.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "El usuario es obligatorio")
    @Size(min = 3, message = "El usuario debe tener al menos 3 caracteres")
    String username,
    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 6, message = "La contrasena debe tener al menos 6 caracteres")
    String password,
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email valido")
    String email,
    @NotBlank(message = "El nombre completo es obligatorio")
    String nombreCompleto
) {}
