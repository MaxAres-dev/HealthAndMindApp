package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    suspend fun saveFood(food:FoodSelected)

    suspend fun getFoodQty(id : Int) : Double

    suspend fun deleteFood(food:FoodSelected)
    // metodo per risposta database
    suspend fun getFood(query : String) : Flow<List<Food>>

    suspend fun updateFood(id : Int, qty : Double)
}