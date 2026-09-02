package com.zest.productapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zest.productapi.entity.RefreshToken;
import com.zest.productapi.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

}
