package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.repository.TrainingCalRepository
import javax.inject.Inject

class PopulateDatabaseUseCase @Inject constructor(
    private val trainingCalRepo : TrainingCalRepository
){
    suspend fun populate () {
        trainingCalRepo.populate()
    }
}