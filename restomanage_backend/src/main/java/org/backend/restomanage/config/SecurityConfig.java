package org.backend.restomanage.config;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.backend.restomanage.exception.ErrorResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(Arrays.asList(
                    "http://localhost:4200",  // Angular
                    "http://localhost:3000"   // React if needed
                ));
                corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                corsConfig.setAllowCredentials(true);
                corsConfig.setMaxAge(3600L);
                return corsConfig;
            }))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(
                    "/api/v1/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                ).permitAll()
                // Client endpoints
                .requestMatchers(
                    "/api/v1/clients/**",
                    "/api/v1/orders/**"
                ).hasRole("CLIENT")
                // Manager endpoints
                .requestMatchers(
                    "/api/v1/managers/**",
                    "/api/v1/restaurants/**",
                    "/api/v1/ingredients/**",
                    "/api/v1/meals/**",
                    "/api/v1/settings/**"
                ).hasRole("MANAGER")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, ex) -> {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(
                        new ObjectMapper().writeValueAsString(new ErrorResponse(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Authentication required"
                        ))
                    );
                })
                .accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write(
                        new ObjectMapper().writeValueAsString(new ErrorResponse(
                            HttpStatus.FORBIDDEN.value(),
                            "Access denied"
                        ))
                    );
                })
            );

        return http.build();
    }
} 