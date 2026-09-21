package com.assessment.auth.exception;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiErrorTest {

    @Test
    void threeArgConstructor_setsFieldsAndTimestamp() {
        ApiError error = new ApiError(404, "NOT_FOUND", "No encontrado");

        assertEquals(404, error.getStatus());
        assertEquals("NOT_FOUND", error.getCode());
        assertEquals("No encontrado", error.getMessage());
        assertNull(error.getDetails());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void builder_setsAllFields() {
        Map<String, String> details = Map.of("campo", "obligatorio");

        ApiError error = ApiError.builder()
                .status(400)
                .code("VALIDATION_ERROR")
                .message("Errores de validacion")
                .details(details)
                .build();

        assertEquals(400, error.getStatus());
        assertEquals("VALIDATION_ERROR", error.getCode());
        assertTrue(error.getDetails().containsKey("campo"));
        assertNotNull(error.getTimestamp());
    }

    @Test
    void noArgsConstructor_createsEmptyInstance() {
        ApiError error = new ApiError();
        assertEquals(0, error.getStatus());
        assertNull(error.getCode());
    }
}
