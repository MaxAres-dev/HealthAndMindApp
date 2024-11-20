package com.example.healthandmind.data.repository.network

import com.example.healthandmind.data.repository.network.model.NutrientRequest
import com.example.healthandmind.data.repository.network.model.NutrientResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NutritionixApiService {

    @POST("v2/natural/nutrients")
    suspend fun getFoodNutrients(@Body request : NutrientRequest) : Response<NutrientResponse>

}
