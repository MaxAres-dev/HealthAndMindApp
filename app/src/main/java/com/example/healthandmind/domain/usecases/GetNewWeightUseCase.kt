package com.example.healthandmind.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class GetNewWeightUseCase @Inject constructor(
    private val getTotalFoodsCalories: GetTotalFoodsCalories,
    private val getTotalTrainingCalories: GetTotalTrainingCalories,
    private val getTdeeUseCase: GetTdeeUseCase,
    private val weightHandlerUseCase: WeightHandlerUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getNewWeight(): Double {
        val tdee = getTdeeUseCase.getTdee()
        val totalTraininCalBurned = getTotalTrainingCalories.getTotalTrainingCalories()
        val totalFoodsCaloriesGained = getTotalFoodsCalories.getTotalCalories()
        val weight = weightHandlerUseCase.getLastElement()
        val deltaWeight = (tdee - (totalFoodsCaloriesGained - totalTraininCalBurned))/7000
        return weight.value + deltaWeight
    }
}