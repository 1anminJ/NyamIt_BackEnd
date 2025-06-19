package com.minjeong.nyamit_be.service;

import com.minjeong.nyamit_be.dto.RecipeRequestDTO;
import com.minjeong.nyamit_be.dto.RecipeResponseDTO;
import com.minjeong.nyamit_be.entity.Meal;
import com.minjeong.nyamit_be.entity.Recipe;
import com.minjeong.nyamit_be.repository.MealRepository;
import com.minjeong.nyamit_be.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MealRepository mealRepository;

    public List<Recipe> getRecipesByMealId(Long mealId) {
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));
        return recipeRepository.findByMeal(meal);
    }

    public void addRecipe(RecipeRequestDTO dto) {
        Meal meal = mealRepository.findById(dto.getMealId())
                .orElseThrow(() -> new IllegalArgumentException("식단이 존재하지 않습니다."));

        Recipe recipe = Recipe.builder()
                .meal(meal)
                .name(dto.getName())
                .ingredients(dto.getIngredients())
                .instructions(dto.getInstructions())
                .build();

        recipeRepository.save(recipe);
    }
    public List<RecipeResponseDTO> getAllRecipesAsDTO() {
        List<Recipe> recipes = recipeRepository.findAll();

        return recipes.stream()
                .map(recipe -> RecipeResponseDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .ingredients(recipe.getIngredients())
                        .instructions(recipe.getInstructions())
                        .mealId(recipe.getMeal().getId())
                        .mealName(recipe.getMeal().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 레시피가 존재하지 않습니다.");
        }
        recipeRepository.deleteById(id);
    }
}
