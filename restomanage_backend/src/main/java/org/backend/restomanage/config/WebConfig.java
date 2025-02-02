package org.backend.restomanage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.http.MediaType;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // Allow multiple frontend origins if needed
                .allowedOrigins(
                        "http://localhost:3000",    // React default port
                        "http://localhost:4200",    // Angular default port
                        "http://localhost:8080"     // Vue default port
                )
                // Allow all common HTTP methods
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
                // Allow all headers
                .allowedHeaders(
                        "Authorization",
                        "Content-Type",
                        "X-Requested-With",
                        "Accept",
                        "Origin",
                        "Access-Control-Request-Method",
                        "Access-Control-Request-Headers"
                )
                // Allow cookies and authentication headers
                .allowCredentials(true)
                // Cache CORS configuration for 1 hour
                .maxAge(3600L);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("v1", MediaType.APPLICATION_JSON);
    }
}