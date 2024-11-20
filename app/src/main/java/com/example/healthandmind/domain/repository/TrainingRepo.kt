package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.TrainingDomain
import kotlinx.coroutines.flow.Flow

interface TrainingRepo {
    fun getTrainingList(date:String): Flow<List<TrainingDomain>>
    suspend fun deleteTraining(training: TrainingDomain)
    suspend fun updateTraining(id : Int, time : Int, calories : Double)
    suspend fun insertTraining(training: TrainingDomain)
}