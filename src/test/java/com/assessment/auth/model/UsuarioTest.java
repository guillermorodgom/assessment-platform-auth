package com.assessment.auth.model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void builder_createsUsuarioWithAllFields() {
        Usuario usuario = Usuario.builder()
                .id(1L)
                .username("admin")
                .password("encoded")
                .email("admin@mail.com")
                .nombreCompleto("Admin User")
                .roles(Set.of(Rol.ADMIN))
                .build();

        assertEquals(1L, usuario.getId());
        assertEquals("admin", usuario.getUsername());
        assertEquals("encoded", usuario.getPassword());
        assertEquals("admin@mail.com", usuario.getEmail());
        assertEquals("Admin User", usuario.getNombreCompleto());
        assertTrue(usuario.getRoles().contains(Rol.ADMIN));
    }

    @Test
    void defaultRoles_isEmptySet() {
        Usuario usuario = Usuario.builder()
                .username("test")
                .password("pass")
                .email("t@mail.com")
                .nombreCompleto("Test")
                .build();

        assertNotNull(usuario.getRoles());
        assertTrue(usuario.getRoles().isEmpty());
    }

    @Test
    void setters_updateFields() {
        Usuario usuario = new Usuario();
        usuario.setUsername("updated");
        usuario.setEmail("updated@mail.com");

        assertEquals("updated", usuario.getUsername());
        assertEquals("updated@mail.com", usuario.getEmail());
    }
}
