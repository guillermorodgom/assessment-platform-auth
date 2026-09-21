package com.assessment.auth.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    @Test
    void businessException_carriesMessage() {
        BusinessException ex = new BusinessException("error de negocio");
        assertEquals("error de negocio", ex.getMessage());
    }

    @Test
    void entityNotFoundException_withEntityAndId() {
        EntityNotFoundException ex = new EntityNotFoundException("Usuario", 42L);
        assertTrue(ex.getMessage().contains("Usuario"));
        assertTrue(ex.getMessage().contains("42"));
    }

    @Test
    void entityNotFoundException_withMessage() {
        EntityNotFoundException ex = new EntityNotFoundException("no existe");
        assertEquals("no existe", ex.getMessage());
    }

    @Test
    void duplicateEntityException_carriesMessage() {
        DuplicateEntityException ex = new DuplicateEntityException("ya existe");
        assertEquals("ya existe", ex.getMessage());
    }

    @Test
    void entityNotFoundException_isBusinessException() {
        assertInstanceOf(BusinessException.class, new EntityNotFoundException("x"));
    }

    @Test
    void duplicateEntityException_isBusinessException() {
        assertInstanceOf(BusinessException.class, new DuplicateEntityException("x"));
    }
}
