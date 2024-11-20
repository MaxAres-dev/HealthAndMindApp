package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.entities.LoginInfo
import javax.inject.Inject

class GetTdeeUseCase @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getWeightHandlerUseCase: WeightHandlerUseCase
) {
    suspend fun getTdee(): Double {
        val userData: LoginInfo = getUserDataUseCase.getUserData()
        val weight = getWeightHandlerUseCase.getLastElement()
        val bmr = when (userData.sex) {
            true -> {
                (10 * weight.value) + (6.25 * userData.height) - (5 * userData.age) + 5
            }
            false -> {
                (10 * weight.value) + (6.25 * userData.height) - (5 * userData.age) - 161
            }
        }
        return bmr * when (userData.activityLevel) {
            "Sedentary" -> 1.2
            "Lightly Active" -> 1.375
            "Moderately Active" -> 1.55
            "Very Active" -> 1.725
            "Super Active" -> 1.9
            else -> 1.2
        }
    }
}