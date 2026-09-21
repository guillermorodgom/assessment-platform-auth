package com.assessment.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    @Test
    void values_containsAdminAndCandidato() {
        Rol[] roles = Rol.values();
        assertEquals(2, roles.length);
        assertEquals(Rol.ADMIN, Rol.valueOf("ADMIN"));
        assertEquals(Rol.CANDIDATO, Rol.valueOf("CANDIDATO"));
    }
}
