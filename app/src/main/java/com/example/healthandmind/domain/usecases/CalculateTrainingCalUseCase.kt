package com.example.healthandmind.domain.usecases

import androidx.room.Insert
import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.domain.entities.TrainingCoeff
import javax.inject.Inject

class CalculateTrainingCalUseCase @Inject constructor() {
    fun calculateTrainingCal(
        trainingCal : TrainingCoeff,
        time: Int,
        weight: Double,
        intensity: String
    ) : Double {
        return trainingCal.coeffCal * time * weight * if(intensity =="high") trainingCal.coeffInt else 1.0
    }
}