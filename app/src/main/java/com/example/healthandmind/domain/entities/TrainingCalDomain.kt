package com.example.healthandmind.domain.entities

data class TrainingCalDomain (
    val id: Int = 0,
    val tipo : String,
    val coefficienteCal : Double, // calorie al minuto
    val coefficienteInt : Double // calorie totali * intensità
) {
    companion object {
        val default = TrainingCalDomain(0, "default", 0.0, 0.0)
    }
}