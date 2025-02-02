package org.backend.restomanage.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    @Test
    void whenValidToken_thenExtractUsername() {
        // Test implementation
    }

    @Test
    void whenTokenExpired_thenInvalid() {
        // Test implementation
    }
} 