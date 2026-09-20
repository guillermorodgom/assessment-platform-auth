package com.assessment.auth.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_returns404() {
        ResponseEntity<ApiError> response = handler.handleNotFound(
                new EntityNotFoundException("Usuario", 99L));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("NOT_FOUND", response.getBody().getCode());
        assertTrue(response.getBody().getMessage().contains("99"));
    }

    @Test
    void handleDuplicate_returns409() {
        ResponseEntity<ApiError> response = handler.handleDuplicate(
                new DuplicateEntityException("Email ya existe"));

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("DUPLICATE", response.getBody().getCode());
    }

    @Test
    void handleBusiness_returns400() {
        ResponseEntity<ApiError> response = handler.handleBusiness(
                new BusinessException("Operacion no permitida"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BUSINESS_ERROR", response.getBody().getCode());
    }

    @Test
    void handleBadCredentials_returns401() {
        ResponseEntity<ApiError> response = handler.handleBadCredentials(
                new BadCredentialsException("Bad credentials"));

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("UNAUTHORIZED", response.getBody().getCode());
    }

    @Test
    void handleValidation_returns400WithDetails() {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "request");
        bindingResult.addError(new FieldError("request", "email", "debe ser un email valido"));
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ApiError> response = handler.handleValidation(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("VALIDATION_ERROR", response.getBody().getCode());
        assertTrue(response.getBody().getDetails().containsKey("email"));
    }

    @Test
    void handleGeneral_returns500() {
        ResponseEntity<ApiError> response = handler.handleGeneral(
                new RuntimeException("unexpected"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("INTERNAL_ERROR", response.getBody().getCode());
    }
}
