package com.example.healthandmind.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class GetTotalFoodsCalories @Inject constructor(
    private val getMealUseCase: GetMealUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTotalCalories(): Double =
        getMealUseCase.getAllMealByDate(LocalDate.now().toString())
            .first().foods.sumOf { it.calories }
}
