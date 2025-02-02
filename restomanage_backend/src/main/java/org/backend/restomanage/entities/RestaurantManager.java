package org.backend.restomanage.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.backend.restomanage.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "restaurant_managers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantManager implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Please provide a valid email address")
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Please provide a valid phone number")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "restaurantManager", cascade = CascadeType.ALL)
    private List<RestaurantSettings> restaurants = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper methods to maintain bidirectional relationship
    public void addRestaurant(RestaurantSettings restaurant) {
        restaurants.add(restaurant);
        restaurant.setRestaurantManager(this);
    }

    public void removeRestaurant(RestaurantSettings restaurant) {
        restaurants.remove(restaurant);
        restaurant.setRestaurantManager(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + Role.MANAGER.name()));
    }

    @Override
    public String getUsername() {
        return this.username; // Using username field for authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
