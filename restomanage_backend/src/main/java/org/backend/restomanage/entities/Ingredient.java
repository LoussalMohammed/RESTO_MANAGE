package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private double quantity;

    @Column(nullable = false)
    private double minQuantity;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
    private List<MealIngredient> mealIngredients = new ArrayList<>();
}
