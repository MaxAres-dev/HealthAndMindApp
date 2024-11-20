package com.example.healthandmind.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthandmind.domain.entities.TrainingCalDomain
import com.example.healthandmind.domain.entities.TrainingCoeff
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.domain.repository.TrainingCalRepository
import com.example.healthandmind.domain.repository.TrainingRepo
import java.time.LocalDate
import javax.inject.Inject

class TrainingHandlerUseCase @Inject constructor(
    private val trainingRepo: TrainingRepo,
    private val trainingCalRepo : TrainingCalRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getTrainingList() = trainingRepo.getTrainingList(date = LocalDate.now().toString()) // yyyy-mm-dd

    suspend fun deleteTraining(training : TrainingDomain) = trainingRepo.deleteTraining(training)

    suspend fun updateTraining(id: Int, time: Int, calories : Double) = trainingRepo.updateTraining(id, time, calories)

    suspend fun getTrainingTypes() = trainingCalRepo.getTrainingTypes()

    suspend fun getTrainingCal(tipo: String) : TrainingCoeff = trainingCalRepo.getCoefficents(tipo)

    suspend fun insertTraining (training : TrainingDomain) = trainingRepo.insertTraining(training)
}