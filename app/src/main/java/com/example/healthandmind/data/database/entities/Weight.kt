package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Weight(

    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var dailyWeight: Double,
    var date : String
)
