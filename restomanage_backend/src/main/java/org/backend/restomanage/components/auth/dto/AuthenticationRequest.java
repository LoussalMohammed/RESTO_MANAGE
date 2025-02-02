package org.backend.restomanage.components.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Username/Email is required")
    private String username; // This can be either username or email
    
    @NotBlank(message = "Password is required")
    private String password;
} 