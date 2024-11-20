package com.example.healthandmind.domain.usecases

import com.example.healthandmind.data.loginUiToLoginInfo
import com.example.healthandmind.domain.repository.UserRepository
import com.example.healthandmind.ui.screen.Login1String
import javax.inject.Inject

class SaveUserLoginDataUseCase @Inject constructor (
    private val userRepo : UserRepository,
) {
    suspend fun saveUserData(loginUi: Login1String){
        userRepo.saveUserData(loginUiToLoginInfo(loginUi))
    }
}