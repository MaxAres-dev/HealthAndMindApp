package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.TrainingCalDao
import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.domain.entities.TrainingCoeff
import com.example.healthandmind.domain.repository.TrainingCalRepository
import javax.inject.Inject

class TrainingCalRepositoryImpl @Inject constructor(
    private val trainingCalDao: TrainingCalDao
) : TrainingCalRepository {
    override suspend fun populate() {
        trainingCalDao.insert()
    }

    override suspend fun getCoefficents(tipo: String): TrainingCoeff {
        return TrainingCoeff(
            coeffCal = trainingCalDao.getTrainingCoefficent(tipo),
            coeffInt = trainingCalDao.getTrainingIntensity(tipo)
        )
    }

    override suspend fun getTrainingTypes(): List<TrainingCalDomain> {
        return trainingCalDao.getTrainingTypes().map { item ->
            TrainingCalDomain(
                id = item.id,
                tipo = item.tipo,
                coefficienteCal = item.coefficienteCal,
                coefficienteInt = item.coefficienteInt
            )
        }
    }
}