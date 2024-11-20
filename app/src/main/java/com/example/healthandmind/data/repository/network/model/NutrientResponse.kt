package com.example.healthandmind.data.repository.network.model

import com.google.gson.annotations.SerializedName

data class NutrientResponse(
    val foods: List<FoodResponse>,
) {
    data class FoodResponse (
        @SerializedName("food_name") val foodName: String,
        @SerializedName("nf_calories") val calories : Double,
        @SerializedName("nf_total_fat") val totalFat : Double,
        @SerializedName("nf_total_carbohydrate") val totalCarbohydrate : Double,
        @SerializedName("nf_protein") val totalProtein : Double,
        @SerializedName("serving_weight_grams") val servingWeightGrams : Double,
        // @SerializedName("photo") val photo : PhotoResponse
    )
    /*
    data class PhotoResponse (
        val thumb : String
    )

     */
    companion object {
        val default : List<FoodResponse> =
            listOf(FoodResponse(
                foodName = "no food name found",
                calories = 0.0,
                totalCarbohydrate = 0.0,
                totalFat = 0.0,
                totalProtein = 0.0,
                servingWeightGrams = 0.0,
                // photo = PhotoResponse(thumb = "")
            )
            )
    }
}
