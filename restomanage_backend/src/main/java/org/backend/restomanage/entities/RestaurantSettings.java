package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "restaurant_settings")
public class RestaurantSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
        name = "restaurant_business_hours",
        joinColumns = @JoinColumn(name = "restaurant_id"),
        inverseJoinColumns = @JoinColumn(name = "business_hours_id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, BusinessHours> businessHours = new HashMap<>();

    @ManyToOne
    private RestaurantManager restaurantManager;

    @Column(nullable = false)
    private int tableReservationTimeLimit = 30; // in minutes
}
