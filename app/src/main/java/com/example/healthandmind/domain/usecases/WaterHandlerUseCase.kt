package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.entities.WaterProgress
import com.example.healthandmind.domain.repository.WaterRepository
import javax.inject.Inject

class WaterHandlerUseCase @Inject constructor(
    private val waterRepository: WaterRepository
) {
    fun getWaterByDate(date: String) = waterRepository.getWaterByDate(date = date)
    suspend fun getLastElement() = waterRepository.getLastElement()
    suspend fun saveWater(water: WaterProgress) = waterRepository.saveWater(water)
    suspend fun updateWater(id : Int, qty : Int) = waterRepository.addWater(id = id , qty = qty)
}