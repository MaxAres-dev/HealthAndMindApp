package com.example.healthandmind.domain.entities

import java.time.LocalDate

data class TrainingDomain (
    val id: Int,
    val trainingType : String,
    val trainingTime : Int,
    val trainingKcal : Double,
    val intensity : Double,
    val date : String
) {
    companion object {
        val default = TrainingDomain(
            id = 0,
            trainingType = "",
            trainingTime = 0,
            trainingKcal = 0.0,
            intensity = 0.0,
            date = ""
        )
    }
}