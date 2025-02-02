package org.backend.restomanage.components.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.auth.dto.AuthenticationRequest;
import org.backend.restomanage.components.auth.dto.AuthenticationResponse;
import org.backend.restomanage.components.auth.dto.RegisterRequest;
import org.backend.restomanage.components.auth.dto.ManagerRegisterRequest;
import org.backend.restomanage.components.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/client/login")
    public ResponseEntity<AuthenticationResponse> authenticateClient(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticateClient(request));
    }

    @PostMapping("/manager/login")
    public ResponseEntity<AuthenticationResponse> authenticateManager(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticateManager(request));
    }

    @PostMapping("/client/register")
    public ResponseEntity<AuthenticationResponse> registerClient(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.registerClient(request));
    }

    @PostMapping("/manager/register")
    public ResponseEntity<AuthenticationResponse> registerManager(
            @Valid @RequestBody ManagerRegisterRequest request
    ) {
        return ResponseEntity.ok(authService.registerManager(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.substring(7)); // Remove "Bearer " prefix
        return ResponseEntity.ok().build();
    }
} 