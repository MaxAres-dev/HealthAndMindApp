package com.example.healthandmind.domain.usecases

import com.example.healthandmind.data.loginInfoToLoginUi
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.repository.UserRepository
import com.example.healthandmind.ui.screen.LoginDataString
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserData(): LoginInfo {
        return userRepository.getUserData()
    }
    fun getUserGoal() : Flow<String> {
        return userRepository.getUserGoal()
    }

    suspend fun getUserDataVM(): LoginDataString {
        return loginInfoToLoginUi(userRepository.getUserData())
    }

}