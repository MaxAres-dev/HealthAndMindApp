package com.example.healthandmind.domain.usecases

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetMealUseCase @Inject constructor(
   private val  mealRepo : MealRepository
) {

    // mi restituisce il meal ovvero l'insieme di cibi consumati dall'utente per uno specifico pasto nella data corrente
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getMeal(mealType : String) : Flow<Meal> {
        return mealRepo.getMeal(mealType = mealType, date = LocalDate.now().toString())
    }

    fun getAllMealByDate(date : String) : Flow<Meal> {
        return mealRepo.getAllMealsByDate(date = date)
    }

    suspend fun getMealById(id : Int)  : FoodSelected {
       return mealRepo.getMealById(id = id)
    }
}