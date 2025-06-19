package com.minjeong.nyamit_be.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDTO {
    private Long id;
    private String name;
    private String ingredients;
    private String instructions;
    private Long mealId;
    private String mealName;
}



