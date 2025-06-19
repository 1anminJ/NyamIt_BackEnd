package com.minjeong.nyamit_be.dto;

import lombok.Data;

import java.util.List;

@Data
public class MealWithRecipesRequestDTO {
    private String name;
    private String description;
    private String mealType;
    private RecipeDTO recipe;
}
