package com.minjeong.nyamit_be.dto;

import com.minjeong.nyamit_be.entity.MealType;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealRequestDTO {
    private MealType mealType;
    private String name;
    private String description;
    private RecipeDTO recipe; // 1:1 관계에서 단일 레시피
}
