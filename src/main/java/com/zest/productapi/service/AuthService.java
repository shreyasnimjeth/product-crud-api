package com.zest.productapi.service;

import com.zest.productapi.dto.request.LoginRequest;
import com.zest.productapi.dto.request.RefreshTokenRequest;
import com.zest.productapi.dto.request.RegisterRequest;
import com.zest.productapi.dto.response.AuthResponse;
import com.zest.productapi.entity.RefreshToken;
import com.zest.productapi.entity.Role;
import com.zest.productapi.entity.User;
import com.zest.productapi.repository.UserRepository;
import com.zest.productapi.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {


private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;
private final UserDetailsService userDetailsService;
private final JwtService jwtService;
private final RefreshTokenService refreshTokenService;

public void register(RegisterRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
        throw new IllegalArgumentException("Username is already registered");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
        throw new IllegalArgumentException("Email is already registered");
    }

    User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .enabled(true)
            .build();

    userRepository.save(user);
}

public AuthResponse login(LoginRequest request) {

    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );

    User user = userRepository.findByUsername(
            request.getUsername()
    ).orElseThrow(() ->
            new IllegalArgumentException(
                    "User not found"
            )
    );

    UserDetails userDetails =
            userDetailsService.loadUserByUsername(
                    request.getUsername()
            );

    String accessToken =
            jwtService.generateToken(userDetails);

    RefreshToken refreshToken =
            refreshTokenService.createRefreshToken(user);

    return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken.getToken())
            .tokenType("Bearer")
            .expiresIn(jwtService.getExpiration())
            .build();
}

public AuthResponse refreshAccessToken(
            RefreshTokenRequest request) {

        RefreshToken oldRefreshToken =
                refreshTokenService.verifyRefreshToken(
                        request.getRefreshToken()
                );

        User user = oldRefreshToken.getUser();

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        user.getUsername()
                );

        String newAccessToken =
                jwtService.generateToken(userDetails);

        refreshTokenService.revokeToken(oldRefreshToken);

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpiration())
                .build();
    }


}
