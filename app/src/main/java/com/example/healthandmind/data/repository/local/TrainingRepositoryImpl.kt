package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.TrainingDao
import com.example.healthandmind.data.database.entities.Training
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.domain.repository.TrainingRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrainingRepositoryImpl @Inject constructor(
    private val trainingDao: TrainingDao
) : TrainingRepo {
    override fun getTrainingList(date : String): Flow<List<TrainingDomain>> {
        return trainingDao.observeTrainingList(date).map {
            it.map {
                TrainingDomain(
                    id = it.id,
                    trainingType = it.trainingType,
                    trainingTime = it.trainingTime,
                    trainingKcal = it.trainingKcal,
                    intensity = it.intensity,
                    date = it.date
                )
            }
        }
    }

    override suspend fun deleteTraining(training: TrainingDomain) {
        trainingDao.deleteTraining(Training(
            id = training.id,
            trainingType = training.trainingType,
            trainingTime = training.trainingTime,
            trainingKcal = training.trainingKcal,
            intensity = training.intensity,
            date = training.date
        ))
    }

    override suspend fun updateTraining(id: Int, time: Int, calories: Double) {
        trainingDao.updateTraining(id, time, calories)
    }
    override suspend fun insertTraining(training: TrainingDomain) {
        trainingDao.insertTraining(Training(
            id = training.id,
            trainingType = training.trainingType,
            trainingTime = training.trainingTime,
            trainingKcal = training.trainingKcal,
            intensity = training.intensity,
            date = training.date
        )
        )
    }
}
