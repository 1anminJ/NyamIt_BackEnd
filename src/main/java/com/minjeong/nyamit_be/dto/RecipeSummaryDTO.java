package com.minjeong.nyamit_be.dto;

import com.minjeong.nyamit_be.entity.Recipe;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSummaryDTO {
    private Long id;
    private String name;
    private String instructions;

    public static RecipeSummaryDTO fromEntity(Recipe recipe) {
        return new RecipeSummaryDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getInstructions()
        );
    }
}
