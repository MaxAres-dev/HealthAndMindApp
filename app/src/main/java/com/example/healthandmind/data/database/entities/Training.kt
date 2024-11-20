package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Training(
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val trainingType : String,
    val trainingTime : Int,
    val trainingKcal : Double,
    val intensity : Double,
    val date : String,
)
