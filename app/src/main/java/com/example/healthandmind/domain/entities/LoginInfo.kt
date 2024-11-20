package com.example.healthandmind.domain.entities

data class LoginInfo(

    val email: String,
    val name: String,
    val weight : Double,
    val height : Int,
    val age : Int,
    val sex : Boolean,
    val activityLevel : String,
    val goal : String,
    val isLogged1 : Boolean,
    val isLogged2 : Boolean

)
