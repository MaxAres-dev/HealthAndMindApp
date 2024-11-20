package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetFoodListUseCase @Inject constructor(
    private val foodRepo: FoodRepository
) {
    private val foodListFlow = MutableStateFlow(emptyList<Food>())


    suspend fun getFoodList(query:String) : Flow<List<Food>> {
        return foodRepo.getFood(query)
    }
}