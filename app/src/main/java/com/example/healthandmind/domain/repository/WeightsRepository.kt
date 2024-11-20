package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.WeightDomain

interface WeightsRepository {
    suspend fun getWeightsByDate(date: String): WeightDomain
    suspend fun getAllWeights(): List<WeightDomain>
    suspend fun updateWeight(id: Int, weight: Double)
    suspend fun insertWeight(weight: WeightDomain)
}