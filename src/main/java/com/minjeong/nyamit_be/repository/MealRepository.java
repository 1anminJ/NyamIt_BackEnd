package com.minjeong.nyamit_be.repository;

import com.minjeong.nyamit_be.entity.Meal;
import com.minjeong.nyamit_be.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByMealType(MealType type);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.recipe")
    List<Meal> findAllWithRecipes();
}

