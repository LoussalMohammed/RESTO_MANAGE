package org.backend.restomanage.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "meal_ingredients")
public class MealIngredient {
    @EmbeddedId
    private MealIngredientId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mealId")
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantity;

    @Embeddable
    @Getter
    @Setter
    public static class MealIngredientId implements java.io.Serializable {
        @Column(name = "meal_id")
        private Long mealId;

        @Column(name = "ingredient_id")
        private Long ingredientId;

        public MealIngredientId() {
        }

        public MealIngredientId(Long mealId, Long ingredientId) {
            this.mealId = mealId;
            this.ingredientId = ingredientId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MealIngredientId that = (MealIngredientId) o;

            if (!mealId.equals(that.mealId)) return false;
            return ingredientId.equals(that.ingredientId);
        }

        @Override
        public int hashCode() {
            int result = mealId.hashCode();
            result = 31 * result + ingredientId.hashCode();
            return result;
        }
    }
}
