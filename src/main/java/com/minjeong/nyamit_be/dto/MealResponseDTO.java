package com.minjeong.nyamit_be.dto;

import com.minjeong.nyamit_be.entity.Meal;
import com.minjeong.nyamit_be.entity.MealType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealResponseDTO {
    private Long id;
    private MealType mealType;
    private String name;
    private String description;
    private RecipeSummaryDTO recipe;

    public static MealResponseDTO fromEntity(Meal meal) {
        return MealResponseDTO.builder()
                .id(meal.getId())
                .mealType(meal.getMealType())
                .name(meal.getName())
                .description(meal.getDescription())
                .recipe(
                        meal.getRecipe() != null
                                ? RecipeSummaryDTO.fromEntity(meal.getRecipe())
                                : null
                )
                .build();
    }
}
