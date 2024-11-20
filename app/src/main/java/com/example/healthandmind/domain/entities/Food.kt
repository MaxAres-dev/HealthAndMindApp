package com.example.healthandmind.domain.entities

data class Food (
    val calories : Double,
    val name : String,
    val protein : Double,
    val carbs : Double,
    val fat : Double,
    val servingWeight : Double,
    val kCalCoeff: Double,
)