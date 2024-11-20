package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingCal(
    @PrimaryKey(autoGenerate = true )
    val id: Int = 0,
    val tipo : String,
    val coefficienteCal : Double, // calorie al minuto / peso
    val coefficienteInt : Double // calorie totali * intensità
)