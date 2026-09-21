package com.assessment.auth.service;

import com.assessment.auth.controller.dto.*;
import com.assessment.auth.exception.DuplicateEntityException;
import com.assessment.auth.exception.EntityNotFoundException;
import com.assessment.auth.model.Rol;
import com.assessment.auth.model.Usuario;
import com.assessment.auth.repository.UsuarioRepository;
import com.assessment.auth.security.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock private UsuarioRepository usuarioRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenUtil jwtTokenUtil;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks private AuthServiceImpl authService;

    private Usuario buildUsuario() {
        return Usuario.builder()
                .id(1L)
                .username("testuser")
                .password("encoded")
                .email("test@mail.com")
                .nombreCompleto("Test User")
                .roles(Set.of(Rol.CANDIDATO))
                .build();
    }

    // --- login ---

    @Test
    void login_success_returnsAuthResponse() {
        Usuario usuario = buildUsuario();
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));
        when(jwtTokenUtil.generateToken(eq("testuser"), anyList())).thenReturn("jwt-token");

        AuthResponse response = authService.login(new LoginRequest("testuser", "pass123"));

        assertEquals("jwt-token", response.token());
        assertEquals("testuser", response.username());
        assertEquals("test@mail.com", response.email());
        verify(authenticationManager).authenticate(any());
    }

    // --- register ---

    @Test
    void register_success_savesUsuario() {
        when(usuarioRepository.existsByUsername("newuser")).thenReturn(false);
        when(usuarioRepository.existsByEmail("new@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("pass123")).thenReturn("encoded");

        authService.register(new RegisterRequest("newuser", "pass123", "new@mail.com", "New User"));

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void register_duplicateUsername_throwsException() {
        when(usuarioRepository.existsByUsername("existing")).thenReturn(true);

        assertThrows(DuplicateEntityException.class,
                () -> authService.register(new RegisterRequest("existing", "pass123", "e@mail.com", "Name")));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void register_duplicateEmail_throwsException() {
        when(usuarioRepository.existsByUsername("newuser")).thenReturn(false);
        when(usuarioRepository.existsByEmail("dup@mail.com")).thenReturn(true);

        assertThrows(DuplicateEntityException.class,
                () -> authService.register(new RegisterRequest("newuser", "pass123", "dup@mail.com", "Name")));
        verify(usuarioRepository, never()).save(any());
    }

    // --- getCurrentUser ---

    @Test
    void getCurrentUser_found_returnsUserInfo() {
        Usuario usuario = buildUsuario();
        when(usuarioRepository.findByUsername("testuser")).thenReturn(Optional.of(usuario));

        UserInfoResponse response = authService.getCurrentUser("testuser");

        assertEquals(1L, response.id());
        assertEquals("testuser", response.username());
        assertTrue(response.roles().contains("ROLE_CANDIDATO"));
    }

    @Test
    void getCurrentUser_notFound_throwsException() {
        when(usuarioRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> authService.getCurrentUser("ghost"));
    }
}
