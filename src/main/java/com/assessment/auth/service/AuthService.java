package com.assessment.auth.service;

import com.assessment.auth.controller.dto.AuthResponse;
import com.assessment.auth.controller.dto.LoginRequest;
import com.assessment.auth.controller.dto.RegisterRequest;
import com.assessment.auth.controller.dto.UserInfoResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    void register(RegisterRequest request);
    UserInfoResponse getCurrentUser(String username);
}
