package com.minjeong.nyamit_be.controller;

import com.minjeong.nyamit_be.dto.RecipeRequestDTO;
import com.minjeong.nyamit_be.dto.RecipeResponseDTO;
import com.minjeong.nyamit_be.dto.RecipeUpdateDTO;
import com.minjeong.nyamit_be.entity.Recipe;
import com.minjeong.nyamit_be.repository.RecipeRepository;
import com.minjeong.nyamit_be.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    @GetMapping("/meal/{mealId}")
    public ResponseEntity<List<RecipeResponseDTO>> getRecipesByMealId(@PathVariable Long mealId) {
        List<Recipe> recipes = recipeService.getRecipesByMealId(mealId);
        List<RecipeResponseDTO> dtoList = recipes.stream()
                .map(recipe -> RecipeResponseDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .ingredients(recipe.getIngredients())
                        .instructions(recipe.getInstructions())
                        .mealId(recipe.getMeal().getId())
                        .mealName(recipe.getMeal().getName())
                        .build())
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    @PostMapping
    public ResponseEntity<String> addRecipe(@RequestBody RecipeRequestDTO dto) {
        recipeService.addRecipe(dto);
        return ResponseEntity.ok("레시피 등록 완료");
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipesAsDTO() {
        return ResponseEntity.ok(recipeService.getAllRecipesAsDTO());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable Long id, @RequestBody RecipeUpdateDTO dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피가 존재하지 않습니다."));
        recipe.setInstructions(dto.getInstructions());
        recipeRepository.save(recipe);
        return ResponseEntity.ok("레시피 수정 완료");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("레시피 삭제 완료");
    }
}

