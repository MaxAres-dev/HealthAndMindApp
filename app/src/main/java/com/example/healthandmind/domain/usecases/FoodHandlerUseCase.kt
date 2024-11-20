package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.repository.FoodRepository
import javax.inject.Inject

class FoodHandlerUseCase @Inject constructor (
    private val foodRepo: FoodRepository,
){
    suspend fun getFoodQty(id : Int) : Double {
        return foodRepo.getFoodQty(id)
    }

    suspend fun saveFood(food: FoodSelected) {
        foodRepo.saveFood(food)
    }
    suspend fun deleteFood(food: FoodSelected) {
        foodRepo.deleteFood(food)
    }

    suspend fun updateFoodQty(id : Int, qty : Double) {
        foodRepo.updateFood(id, qty)
    }
}

