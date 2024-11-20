package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.entities.WeightDomain
import com.example.healthandmind.domain.repository.WeightsRepository
import javax.inject.Inject
import kotlin.math.abs

class WeightHandlerUseCase @Inject constructor(
    private val weightRepo : WeightsRepository
) {
    suspend fun getWeeklyWeights() : List<WeightDomain> {
        weightRepo.getAllWeights().let {
            when ((it.size - 7) >= 0) {
                true -> return it.subList(it.size-7, it.size)
                false -> return it
            }
        }
    }
    suspend fun getLastElement() : WeightDomain {
        weightRepo.getAllWeights().let {
            if (it.isEmpty()) {
                return WeightDomain(0.0, "")
            }
            else return it[it.size-1]
        }
    }
    suspend fun updateWeight(id : Int, weight : Double) {
        weightRepo.updateWeight(id, weight)
    }

    suspend fun getWeightByDate(date: String) = weightRepo.getWeightsByDate(date)
    suspend fun insertWeight(weight: WeightDomain) = weightRepo.insertWeight(weight)
}