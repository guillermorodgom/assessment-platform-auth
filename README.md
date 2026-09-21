# Servicio de autenticación

API Spring Boot 3.3.0 y Java 17 para registro, login y emisión de JWT. Comparte la tabla de usuarios y `JWT_SECRET` con mngr.

## Requisitos y configuración

- Java 17, Maven 3.9 o el wrapper incluido y PostgreSQL para el perfil `dev`.
- Crear el esquema según [database](../assessment-platform-database/README.md) antes de iniciar.
- `JWT_SECRET`: misma clave en auth y mngr, de al menos 32 bytes. El valor predeterminado es solo para desarrollo.
- `DB_USERNAME` y `DB_PASSWORD`: `postgres`/`postgres` por defecto.

El perfil predeterminado `dev` usa `jdbc:postgresql://localhost:5432/postgres?currentSchema=assessment_platform`, puerto `8081` y `ddl-auto=validate`. `prod` usa MySQL y `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD`; el proyecto no incluye scripts MySQL.

## Ejecución y API

Desde este directorio ejecute `./mvnw spring-boot:run` y `./mvnw test`. En Windows use `mvnw.cmd`.

| Método | Ruta | Función |
| --- | --- | --- |
| POST | `/api/auth/login` | Entrega JWT |
| POST | `/api/auth/register` | Registra un `CANDIDATO` |
| GET | `/api/auth/me` | Datos del usuario autenticado |

Swagger: `http://localhost:8081/swagger-ui.html`; OpenAPI: `http://localhost:8081/v3/api-docs`.

## Limitación conocida

La configuración permite todo `/api/auth/**` sin token. `/me` usa la identidad autenticada y una petición anónima puede fallar al no existir `Authentication`.
