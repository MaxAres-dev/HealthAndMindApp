package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.entities.Meal
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getMeal(mealType : String, date : String) : Flow<Meal>
    fun getAllMealsByDate(date : String) : Flow<Meal>
    suspend fun getMealById(id : Int) : FoodSelected
}