package com.minjeong.nyamit_be.controller;

import com.minjeong.nyamit_be.dto.MealRequestDTO;
import com.minjeong.nyamit_be.dto.MealResponseDTO;
import com.minjeong.nyamit_be.dto.MealWithRecipesRequestDTO;
import com.minjeong.nyamit_be.entity.Meal;
import com.minjeong.nyamit_be.entity.MealType;
import com.minjeong.nyamit_be.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @GetMapping
    public ResponseEntity<List<MealResponseDTO>> getAllMeals() {
        List<Meal> meals = mealService.getAllMeals();
        List<MealResponseDTO> dtoList = meals.stream()
                .map(MealResponseDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/type/{type}")
    public ResponseEntity<List<Meal>> getMealsByType(@PathVariable MealType type) {
        return ResponseEntity.ok(mealService.getMealsByType(type));
    }

    @PostMapping("/with-recipes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addMealWithRecipes(@RequestBody MealWithRecipesRequestDTO dto) {
        mealService.addMealWithRecipes(dto);
        return ResponseEntity.ok("식단 및 레시피 등록 완료");
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateMeal(@PathVariable Long id, @RequestBody MealRequestDTO dto) {
        mealService.updateMeal(id, dto);
        return ResponseEntity.ok("식단 수정 성공");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return ResponseEntity.ok("식단 삭제 성공");
    }


    @GetMapping("/{id}")
    public ResponseEntity<MealResponseDTO> getMealById(@PathVariable Long id) {
        Meal meal = mealService.getMealById(id);
        return ResponseEntity.ok(MealResponseDTO.fromEntity(meal));
    }

}

