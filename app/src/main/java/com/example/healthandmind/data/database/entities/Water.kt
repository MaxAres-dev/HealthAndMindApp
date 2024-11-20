package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Water(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var quantity : Int,
    var date : String
)
