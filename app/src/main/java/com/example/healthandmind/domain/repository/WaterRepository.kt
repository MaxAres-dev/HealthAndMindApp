package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.WaterProgress
import kotlinx.coroutines.flow.Flow

interface WaterRepository {
    fun getWaterByDate(date: String): Flow<WaterProgress>
    suspend fun getLastElement(): WaterProgress
    suspend fun saveWater(water: WaterProgress) // crea la tupla
    suspend fun addWater(id : Int, qty : Int) // aggiorna la tupla
}