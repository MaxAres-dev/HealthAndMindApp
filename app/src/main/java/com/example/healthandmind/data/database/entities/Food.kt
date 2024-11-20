package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food")
data class Food (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name : String,
    // calories è solo la prima risposta del server, successivamente le calorie tot nel programma sono calcolate come KCalCoeff * servingWeight
    val calories : Double,
    val proteinCoeff : Double,
    val carbsCoeff : Double,
    val fatCoeff : Double,
    val servingWeight : Double,
    val kCalCoeff: Double,
    val mealType : String,
    val date : String
)