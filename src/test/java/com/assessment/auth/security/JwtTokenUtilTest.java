package com.assessment.auth.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        // Secret de 32+ chars para HS256, expiración 1h
        String secret = "miSecretKeyParaTestsDeAlMenos32Caracteres!!";
        jwtTokenUtil = new JwtTokenUtil(secret, 3600000);
    }

    @Test
    void generateToken_returnsNonNullToken() {
        String token = jwtTokenUtil.generateToken("admin", List.of("ROLE_ADMIN"));
        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void getUsernameFromToken_returnsCorrectUsername() {
        String token = jwtTokenUtil.generateToken("candidato1", List.of("ROLE_CANDIDATO"));
        assertEquals("candidato1", jwtTokenUtil.getUsernameFromToken(token));
    }

    @Test
    void getRolesFromToken_returnsCorrectRoles() {
        List<String> roles = List.of("ROLE_ADMIN", "ROLE_CANDIDATO");
        String token = jwtTokenUtil.generateToken("admin", roles);
        assertEquals(roles, jwtTokenUtil.getRolesFromToken(token));
    }

    @Test
    void validateToken_validToken_returnsTrue() {
        String token = jwtTokenUtil.generateToken("user", List.of("ROLE_CANDIDATO"));
        assertTrue(jwtTokenUtil.validateToken(token));
    }

    @Test
    void validateToken_invalidToken_returnsFalse() {
        assertFalse(jwtTokenUtil.validateToken("token.invalido.aqui"));
    }

    @Test
    void validateToken_expiredToken_returnsFalse() {
        // Token con expiración de 0ms (ya expirado)
        JwtTokenUtil expiredUtil = new JwtTokenUtil(
                "miSecretKeyParaTestsDeAlMenos32Caracteres!!", 0);
        String token = expiredUtil.generateToken("user", List.of("ROLE_CANDIDATO"));
        assertFalse(expiredUtil.validateToken(token));
    }
}
