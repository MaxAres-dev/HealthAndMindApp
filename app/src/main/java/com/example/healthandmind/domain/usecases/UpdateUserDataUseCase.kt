package com.example.healthandmind.domain.usecases

import com.example.healthandmind.data.loginStringToLoginInfo
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.repository.UserRepository
import com.example.healthandmind.ui.screen.LoginDataString
import javax.inject.Inject

class UpdateUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun updateUserData(userInfo: LoginInfo) {
        userRepository.updateUserData(userInfo)
    }

    suspend fun updateGoal(goal: String) {
        userRepository.updateGoal(goal)
    }

    suspend fun updateUserDataVM(loginDataString: LoginDataString) {
        val userInfo = loginStringToLoginInfo(loginDataString)
        userRepository.updateUserData(userInfo)
    }
}