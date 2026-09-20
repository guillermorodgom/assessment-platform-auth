package com.assessment.auth.service;

import com.assessment.auth.controller.dto.*;
import com.assessment.auth.exception.DuplicateEntityException;
import com.assessment.auth.exception.EntityNotFoundException;
import com.assessment.auth.model.Rol;
import com.assessment.auth.model.Usuario;
import com.assessment.auth.repository.UsuarioRepository;
import com.assessment.auth.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        Usuario usuario = usuarioRepository.findByUsername(request.username()).orElseThrow();

        List<String> roles = usuario.getRoles().stream()
            .map(rol -> "ROLE_" + rol.name())
            .toList();

        String token = jwtTokenUtil.generateToken(usuario.getUsername(), roles);
        log.info("[AUTH] Login exitoso — usuario: {}", usuario.getUsername());
        return new AuthResponse(token, usuario.getUsername(), usuario.getEmail(),
            usuario.getNombreCompleto(), roles);
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.username())) {
            throw new DuplicateEntityException("El usuario '" + request.username() + "' ya existe");
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new DuplicateEntityException("El email '" + request.email() + "' ya esta registrado");
        }

        Usuario usuario = Usuario.builder()
            .username(request.username())
            .password(passwordEncoder.encode(request.password()))
            .email(request.email())
            .nombreCompleto(request.nombreCompleto())
            .roles(Set.of(Rol.CANDIDATO))
            .build();

        usuarioRepository.save(usuario);
        log.info("[CREAR] Usuario registrado — username: {}", request.username());
    }

    @Override
    public UserInfoResponse getCurrentUser(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("Usuario", username));

        List<String> roles = usuario.getRoles().stream()
            .map(rol -> "ROLE_" + rol.name())
            .toList();

        return new UserInfoResponse(usuario.getId(), usuario.getUsername(),
            usuario.getEmail(), usuario.getNombreCompleto(), roles);
    }
}
