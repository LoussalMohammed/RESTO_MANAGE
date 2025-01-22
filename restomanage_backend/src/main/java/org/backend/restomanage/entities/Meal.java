package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.backend.restomanage.enums.MealCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealCategory category;

    @Column(nullable = false)
    private boolean available = true;

    private String imageUrl;

    @Column(nullable = false)
    private int preparationTime; // in minutes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantSettings restaurant;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MealIngredient> mealIngredients = new HashSet<>();

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

    public void addIngredient(Ingredient ingredient, Double quantity) {
        MealIngredient mealIngredient = new MealIngredient();
        mealIngredient.setId(new MealIngredient.MealIngredientId(this.id, ingredient.getId()));
        mealIngredient.setMeal(this);
        mealIngredient.setIngredient(ingredient);
        mealIngredient.setQuantity(quantity);
        this.mealIngredients.add(mealIngredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        this.mealIngredients.removeIf(mi -> mi.getIngredient().getId().equals(ingredient.getId()));
    }

    public void updateIngredientQuantity(Ingredient ingredient, Double quantity) {
        this.mealIngredients.stream()
                .filter(mi -> mi.getIngredient().getId().equals(ingredient.getId()))
                .findFirst()
                .ifPresent(mi -> mi.setQuantity(quantity));
    }
}
