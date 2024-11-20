package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.FoodDao
import com.example.healthandmind.data.foodResponseToFoodList
import com.example.healthandmind.data.foodSelectedToFood
import com.example.healthandmind.data.repository.network.NutritionixApiService
import com.example.healthandmind.data.repository.network.model.NutrientRequest
import com.example.healthandmind.data.repository.network.model.NutrientResponse
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val apiService: NutritionixApiService,
    private val foodDao: FoodDao
) : FoodRepository {
    override suspend fun saveFood(food: FoodSelected) {
        foodDao.insert(foodSelectedToFood(food))
    }

    override suspend fun getFoodQty(id: Int): Double {
        return foodDao.getQty(id)
    }
    override suspend fun updateFood(id: Int, qty: Double) {
        foodDao.update(id, qty)
    }

    override suspend fun deleteFood(food: FoodSelected) {
        val convertedValue = foodSelectedToFood(food)
        foodDao.delete(convertedValue)
    }
    override suspend fun getFood(query: String): Flow<List<Food>> {
        val response = apiService.getFoodNutrients(NutrientRequest(query = query))
        return if (response.isSuccessful) {
            flowOf(foodResponseToFoodList((response.body()?.foods ?: NutrientResponse.default)))
        } else emptyFlow()
    }
}