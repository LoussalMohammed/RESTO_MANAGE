package org.backend.restomanage.config;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.components.manager.repository.RestaurantManagerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ClientRepository clientRepository;
    private final RestaurantManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            if (username == null || username.trim().isEmpty()) {
                throw new UsernameNotFoundException("Username cannot be empty");
            }

            try {
                // Try to find user as client
                var client = clientRepository.findByEmail(username.toLowerCase());
                if (client.isPresent()) {
                    return client.get();
                }

                // Try to find user as manager
                var manager = managerRepository.findByUsernameOrEmail(
                        username.toLowerCase(),
                        username.toLowerCase()
                );
                if (manager.isPresent()) {
                    return manager.get();
                }

                throw new UsernameNotFoundException("User not found: " + username);
            } catch (Exception e) {
                throw new UsernameNotFoundException("Error during user lookup: " + e.getMessage());
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}