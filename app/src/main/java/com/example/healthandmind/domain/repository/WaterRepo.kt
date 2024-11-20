package com.example.healthandmind.domain.repository

interface WaterRepo {
    suspend fun insertWater(quantity : Int, date : String)
    suspend fun updateWater(quantity : Int, date : String)
}