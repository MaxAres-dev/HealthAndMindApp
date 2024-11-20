package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.WaterDao
import com.example.healthandmind.data.database.entities.Water
import com.example.healthandmind.domain.entities.WaterProgress
import com.example.healthandmind.domain.repository.WaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WaterRepositoryImpl @Inject constructor(
    private val waterDao: WaterDao
) : WaterRepository {

    override suspend fun getLastElement(): WaterProgress {
        return waterDao.getLastElement().let {
            WaterProgress(
                id = it.id,
                quantity = it.quantity,
                date = it.date
            )
        }
    }

    override fun getWaterByDate(date: String) : Flow<WaterProgress> {
        return waterDao.getWaterByDate(date).map {
            WaterProgress(
                id = it.id,
                quantity = it.quantity,
                date = it.date
            )
        }
    }

    override suspend fun addWater(id : Int, qty : Int) {
        waterDao.updateWater(
            id = id,
            quantity = qty
        )
    }

    override suspend fun saveWater(water: WaterProgress) {
        waterDao.insertWater(Water(
            id = 0,
            quantity = water.quantity,
            date = water.date
        ))
    }
}