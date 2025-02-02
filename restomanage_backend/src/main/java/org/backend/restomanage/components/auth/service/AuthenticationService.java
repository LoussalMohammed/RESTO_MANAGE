package org.backend.restomanage.components.auth.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.auth.dto.AuthenticationRequest;
import org.backend.restomanage.components.auth.dto.AuthenticationResponse;
import org.backend.restomanage.components.auth.dto.RegisterRequest;
import org.backend.restomanage.components.auth.dto.ManagerRegisterRequest;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.components.manager.repository.RestaurantManagerRepository;
import org.backend.restomanage.entities.Client;
import org.backend.restomanage.entities.RestaurantManager;
import org.backend.restomanage.exception.AuthenticationException;
import org.backend.restomanage.security.JwtService;
import org.backend.restomanage.security.Role;
import org.backend.restomanage.security.TokenBlacklist;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ClientRepository clientRepository;
    private final RestaurantManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklist tokenBlacklist;

    public AuthenticationResponse authenticateClient(AuthenticationRequest request) {
        Client client = clientRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        authenticate(request.getUsername(), request.getPassword());

        String jwtToken = jwtService.generateToken(client);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.CLIENT)
                .userId(client.getId())
                .name(client.getFirstName() + " " + client.getLastName())
                .build();
    }

    public AuthenticationResponse authenticateManager(AuthenticationRequest request) {
        RestaurantManager manager = managerRepository.findByUsernameOrEmail(
                request.getUsername(), request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Invalid username/email or password"));

        authenticate(request.getUsername(), request.getPassword());

        String jwtToken = jwtService.generateToken(manager);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.MANAGER)
                .userId(manager.getId())
                .name(manager.getFirstName() + " " + manager.getLastName())
                .build();
    }

    public AuthenticationResponse registerClient(RegisterRequest request) {
        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new AuthenticationException("Email already exists");
        }

        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        clientRepository.save(client);
        String jwtToken = jwtService.generateToken(client);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.CLIENT)
                .userId(client.getId())
                .name(client.getFirstName() + " " + client.getLastName())
                .build();
    }

    public AuthenticationResponse registerManager(ManagerRegisterRequest request) {
        if (managerRepository.existsByUsername(request.getUsername())) {
            throw new AuthenticationException("Username already exists");
        }
        if (managerRepository.existsByEmail(request.getEmail())) {
            throw new AuthenticationException("Email already exists");
        }

        RestaurantManager manager = RestaurantManager.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername().toLowerCase())
                .email(request.getEmail().toLowerCase())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        managerRepository.save(manager);
        String jwtToken = jwtService.generateToken(manager);
        
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(Role.MANAGER)
                .userId(manager.getId())
                .name(manager.getFirstName() + " " + manager.getLastName())
                .build();
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (Exception e) {
            throw new AuthenticationException("Invalid credentials");
        }
    }

    public void logout(String token) {
        tokenBlacklist.blacklist(token);
    }
} 