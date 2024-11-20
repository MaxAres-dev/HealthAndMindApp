package com.example.healthandmind.domain.entities

data class FoodSelected(
    val id : Int,
    val calories : Double,
    val name : String,
    val proteinCoeff : Double,
    val carbsCoeff : Double,
    val fatCoeff : Double,
    val servingWeight : Double,
    val kCalCoeff: Double,
    val mealType : String,
    val date : String = ""
) {
    companion object {
        val default : FoodSelected = FoodSelected(
                id = 0,
                calories = 0.0,
                name = "",
                proteinCoeff = 0.0,
                carbsCoeff = 0.0,
                fatCoeff = 0.0,
                servingWeight = 0.0,
                kCalCoeff = 0.0,
                mealType = ""
            )
    }
}
