package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.domain.entities.TrainingCoeff

interface TrainingCalRepository {
    suspend fun populate()
    suspend fun getTrainingTypes() : List<TrainingCalDomain>
    suspend fun getCoefficents(tipo : String) : TrainingCoeff
}