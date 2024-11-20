package com.example.healthandmind.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetTotalTrainingCalories @Inject constructor(
    private val trainingHandlerUseCase: TrainingHandlerUseCase
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTotalTrainingCalories(): Double {
        return trainingHandlerUseCase.getTrainingList()
            .first()
            .takeIf { it.isNotEmpty() }
            ?.sumOf { it.trainingKcal }
            ?: 0.0
    }

}
