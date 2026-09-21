package com.assessment.auth.security;

import com.assessment.auth.model.Rol;
import com.assessment.auth.model.Usuario;
import com.assessment.auth.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock private UsuarioRepository usuarioRepository;
    @InjectMocks private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_found_returnsUserDetails() {
        Usuario usuario = Usuario.builder()
                .username("admin")
                .password("encoded")
                .roles(Set.of(Rol.ADMIN))
                .build();
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        UserDetails details = service.loadUserByUsername("admin");

        assertEquals("admin", details.getUsername());
        assertEquals("encoded", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_notFound_throwsException() {
        when(usuarioRepository.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("ghost"));
    }
}
