package com.example.healthandmind.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var email: String,
    var weight: Double,
    var height: Int,
    var age : Int,
    var sex : Boolean,
    var activityLevel : String,
    var goal : String,
    var isLogged1 : Boolean,
    var isLogged2 : Boolean
)
