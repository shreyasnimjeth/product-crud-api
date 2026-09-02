package com.zest.productapi.controller;

import com.zest.productapi.dto.request.LoginRequest;
import com.zest.productapi.dto.request.RefreshTokenRequest;
import com.zest.productapi.dto.request.RegisterRequest;
import com.zest.productapi.dto.response.AuthResponse;
import com.zest.productapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


private final AuthService authService;

@PostMapping("/register")
public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {

    authService.register(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
}

@PostMapping("/login")
public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {

    return ResponseEntity.ok(
            authService.login(request));
}

@PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(authService.refreshAccessToken(request));
    }


}
