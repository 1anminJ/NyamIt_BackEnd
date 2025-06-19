package com.minjeong.nyamit_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeRequestDTO {
    private Long mealId;          // 연결된 식단 ID
    private String name;          // 레시피명 (ex. 닭가슴살 샐러드)
    private String ingredients;   // 재료 목록 (예: 닭가슴살, 양상추, 방울토마토)
    private String instructions;  // 만드는 방법
}
