package com.minjeong.nyamit_be.service;

import com.minjeong.nyamit_be.dto.MealRequestDTO;
import com.minjeong.nyamit_be.dto.MealWithRecipesRequestDTO;
import com.minjeong.nyamit_be.entity.Meal;
import com.minjeong.nyamit_be.entity.MealType;
import com.minjeong.nyamit_be.entity.Recipe;
import com.minjeong.nyamit_be.repository.MealRepository;
import com.minjeong.nyamit_be.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {

    private final MealRepository mealRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    public void addMealWithRecipes(MealWithRecipesRequestDTO dto) {
        Meal meal = Meal.builder()
                .mealType(MealType.valueOf(dto.getMealType()))
                .name(dto.getName())
                .description(dto.getDescription())
                .build();

        Recipe recipe = Recipe.builder()
                .name(dto.getRecipe().getName())
                .ingredients(dto.getRecipe().getIngredients())
                .instructions(dto.getRecipe().getInstructions())
                .meal(meal)
                .build();

        meal.setRecipe(recipe); // 양방향 연결
        mealRepository.save(meal); // cascade 적용 시 recipe도 함께 저장
    }


    public List<Meal> getAllMeals() {
        return mealRepository.findAllWithRecipes();
    }

    public List<Meal> getMealsByType(MealType type) {
        return mealRepository.findByMealType(type);
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));
    }

    @Transactional
    public void updateMeal(Long id, MealRequestDTO dto) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));

        meal.setMealType(dto.getMealType());
        meal.setName(dto.getName());
        meal.setDescription(dto.getDescription());

        Recipe recipe = meal.getRecipe();
        if (recipe != null) {
            recipe.setName(dto.getRecipe().getName());
            recipe.setIngredients(dto.getRecipe().getIngredients());
            recipe.setInstructions(dto.getRecipe().getInstructions());
        } else {
            // 없는 경우 새로 생성
            Recipe newRecipe = Recipe.builder()
                    .name(dto.getRecipe().getName())
                    .ingredients(dto.getRecipe().getIngredients())
                    .instructions(dto.getRecipe().getInstructions())
                    .meal(meal)
                    .build();
            meal.setRecipe(newRecipe);
        }
    }

    @Transactional
    public void deleteMeal(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 식단이 존재하지 않습니다."));
        mealRepository.delete(meal); // cascade 설정으로 recipe도 함께 삭제됨
    }
}
