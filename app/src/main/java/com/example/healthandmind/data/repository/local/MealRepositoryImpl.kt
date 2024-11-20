package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.FoodDao
import com.example.healthandmind.data.foodDbToFoodSelected
import com.example.healthandmind.data.listOfFoodToMeal
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val foodDao: FoodDao
) : MealRepository {
    override fun getMeal(mealType: String, date: String): Flow<Meal> {
        return foodDao.getMeal(mealType = mealType, date = date).map {
            listOfFoodToMeal(it)
        }
    }

    override fun getAllMealsByDate(date: String): Flow<Meal> {
        return foodDao.getAllMealsByDate(date).map {
            listOfFoodToMeal(it)
        }
    }

    override suspend fun getMealById(id: Int): FoodSelected {
        // il fatto del let { listOf(it) } è inutile, è solo per runnare
        return foodDbToFoodSelected(foodDao.getMealById(id))
    }
}