package com.example.healthandmind.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.healthandmind.data.database.entities.User
import com.example.healthandmind.data.repository.network.model.NutrientResponse
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.ui.screen.LoginDataString
import com.example.healthandmind.ui.screen.Login1String
import java.time.LocalDate

fun foodResponseToFood(foodResponse : NutrientResponse.FoodResponse) : Food {
    return Food(
        calories = foodResponse.calories,
        name = foodResponse.foodName,
        protein = foodResponse.totalProtein,
        carbs = foodResponse.totalCarbohydrate,
        fat = foodResponse.totalFat,
        servingWeight = foodResponse.servingWeightGrams,
        kCalCoeff = foodResponse.calories/foodResponse.servingWeightGrams
    )
}

fun foodDbToFoodSelected (foodDb : com.example.healthandmind.data.database.entities.Food) : FoodSelected {
    return FoodSelected(
        id = foodDb.id,
        calories = foodDb.calories,
        name = foodDb.name,
        proteinCoeff = foodDb.proteinCoeff,
        carbsCoeff = foodDb.carbsCoeff,
        fatCoeff = foodDb.fatCoeff,
        servingWeight = foodDb.servingWeight,
        kCalCoeff = foodDb.kCalCoeff,
        mealType = foodDb.mealType,
        date = foodDb.date
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun foodToFoodSelected(food: Food, mealType : String ) : FoodSelected {
    return FoodSelected(
        //inserisco id = 0 perchè poi nella insert me lo autogenera
        id = 0,
        calories = food.calories,
        name = food.name,
        proteinCoeff = food.protein/food.servingWeight,
        carbsCoeff = food.carbs/food.servingWeight,
        fatCoeff = food.fat/food.servingWeight,
        servingWeight = food.servingWeight,
        kCalCoeff = food.kCalCoeff,
        mealType = mealType,
        date = LocalDate.now().toString()
    )
}
fun foodSelectedToFood(foodSelected : FoodSelected) : com.example.healthandmind.data.database.entities.Food {
    return com.example.healthandmind.data.database.entities.Food(
        id = foodSelected.id,
        calories = foodSelected.calories,
        name = foodSelected.name,
        proteinCoeff = foodSelected.proteinCoeff,
        carbsCoeff = foodSelected.carbsCoeff,
        fatCoeff = foodSelected.fatCoeff,
        servingWeight = foodSelected.servingWeight,
        kCalCoeff = foodSelected.kCalCoeff,
        mealType = foodSelected.mealType,
        date = foodSelected.date
    )
}

fun foodResponseToFoodList(foodResponse : List<NutrientResponse.FoodResponse>) : List<Food> {
    val result = foodResponse.map {
        foodResponseToFood(it)
    }
    return result

}

fun loginInfoToUser(loginInfo : LoginInfo) : User {
    return User (
        name = loginInfo.name,
        email = loginInfo.email,
        weight = loginInfo.weight,
        height = loginInfo.height,
        age = loginInfo.age,
        sex = loginInfo.sex,
        activityLevel = loginInfo.activityLevel,
        goal = loginInfo.goal,
        isLogged1 = loginInfo.isLogged1,
        isLogged2 =  loginInfo.isLogged2

    )
}

fun listOfFoodToMeal ( listOfFood : List<com.example.healthandmind.data.database.entities.Food>) : Meal {
    return Meal(foods = listOfFood.map {
        FoodSelected(
            id = it.id,
            calories = it.calories,
            name = it.name,
            proteinCoeff = it.proteinCoeff,
            carbsCoeff = it.carbsCoeff,
            fatCoeff = it.fatCoeff,
            servingWeight = it.servingWeight,
            kCalCoeff = it.kCalCoeff,
            mealType = it.mealType,
            date = it.date
        )
    }
    )
}

fun loginUiToLoginInfo(loginInfoString : Login1String) : LoginInfo {
    return LoginInfo(
        name = loginInfoString.name,
        email = loginInfoString.email,
        weight = loginInfoString.weight.toDouble(),
        height = 0,
        age = 0,
        sex = false,
        activityLevel = "",
        goal = "",
        isLogged1 = true,
        isLogged2 = false
    )
}

fun loginInfoToLoginUi(loginInfo: LoginInfo) : LoginDataString {
    return LoginDataString(
        name = loginInfo.name,
        email = loginInfo.email,
        weight = loginInfo.weight.toString(),
        height = loginInfo.height.toString(),
        age = loginInfo.age.toString(),
        sex = loginInfo.sex.toString(),
        activityLevel = loginInfo.activityLevel,
        goal = loginInfo.goal
    )
}

fun loginStringToLoginInfo(loginInfoString : LoginDataString) : LoginInfo {
    return LoginInfo(
        name = loginInfoString.name,
        email = loginInfoString.email,
        weight = loginInfoString.weight.toDouble(),
        height = loginInfoString.height.toInt(),
        age = loginInfoString.age.toInt(),
        sex = when(loginInfoString.sex){
            "Male"-> true
            "Female"->false
            else -> throw IllegalArgumentException("Invalid sex value: ${loginInfoString.sex}")
        },
        goal = loginInfoString.goal,
        activityLevel = loginInfoString.activityLevel,
        isLogged1 = true,
        isLogged2 = true
    )
}
