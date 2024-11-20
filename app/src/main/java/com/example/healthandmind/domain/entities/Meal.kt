package com.example.healthandmind.domain.entities

data class Meal(
    val foods : List<FoodSelected>,
) {
    companion object {
        val default : Meal = Meal( foods = emptyList())
    }
}
