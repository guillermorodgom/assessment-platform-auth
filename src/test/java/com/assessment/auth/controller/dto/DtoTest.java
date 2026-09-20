package com.assessment.auth.controller.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void loginRequest_accessors() {
        LoginRequest req = new LoginRequest("admin", "pass123");
        assertEquals("admin", req.username());
        assertEquals("pass123", req.password());
    }

    @Test
    void registerRequest_accessors() {
        RegisterRequest req = new RegisterRequest("user", "pass123", "u@mail.com", "User Name");
        assertEquals("user", req.username());
        assertEquals("pass123", req.password());
        assertEquals("u@mail.com", req.email());
        assertEquals("User Name", req.nombreCompleto());
    }

    @Test
    void authResponse_accessors() {
        List<String> roles = List.of("ROLE_ADMIN");
        AuthResponse res = new AuthResponse("token", "admin", "a@mail.com", "Admin", roles);
        assertEquals("token", res.token());
        assertEquals("admin", res.username());
        assertEquals("a@mail.com", res.email());
        assertEquals("Admin", res.nombreCompleto());
        assertEquals(roles, res.roles());
    }

    @Test
    void userInfoResponse_accessors() {
        List<String> roles = List.of("ROLE_CANDIDATO");
        UserInfoResponse res = new UserInfoResponse(1L, "cand", "c@mail.com", "Cand Name", roles);
        assertEquals(1L, res.id());
        assertEquals("cand", res.username());
        assertEquals("c@mail.com", res.email());
        assertEquals("Cand Name", res.nombreCompleto());
        assertEquals(roles, res.roles());
    }
}
