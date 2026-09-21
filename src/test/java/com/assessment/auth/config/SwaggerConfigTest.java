package com.assessment.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest {

    private final SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void customOpenAPI_returnsConfiguredOpenAPI() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertNotNull(openAPI);
        assertEquals("Assessment Platform - Auth API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("Bearer"));
    }
}
