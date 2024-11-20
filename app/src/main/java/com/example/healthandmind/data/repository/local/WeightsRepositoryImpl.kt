package com.example.healthandmind.data.repository.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthandmind.data.database.dao.WeightDao
import com.example.healthandmind.data.database.entities.Weight
import com.example.healthandmind.domain.entities.WeightDomain
import com.example.healthandmind.domain.repository.WeightsRepository
import javax.inject.Inject

class WeightsRepositoryImpl @Inject constructor(
    private val weightsDao: WeightDao
) : WeightsRepository {

    override suspend fun getWeightsByDate(date: String): WeightDomain {
        return weightsDao.getWeightByDate(date = date).let {
            WeightDomain(value = it.dailyWeight, date = it.date, id = it.id)
        }
    }

    override suspend fun getAllWeights(): List<WeightDomain> {
        return weightsDao.getAllWeights().map {
            WeightDomain(value = it.dailyWeight, date = it.date, id = it.id)
        }
    }

    override suspend fun updateWeight(id: Int, weight: Double) {
        weightsDao.updateWeight(id = id, weight = weight)
    }

    override suspend fun insertWeight(weight: WeightDomain) {
        weightsDao.insertWeight(Weight(dailyWeight = weight.value, date = weight.date, id = weight.id))
    }
}