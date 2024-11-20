package com.example.healthandmind.domain.usecases

import com.example.healthandmind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginHandlerUseCase @Inject constructor(
    private val userRepo : UserRepository
){

    suspend fun setIsLogged1(value: Boolean) {
        userRepo.updateIsLogged1(isLogged1= value)
    }

    suspend fun setIsLogged2(value : Boolean) {
        userRepo.updateIsLogged2(isLogged2 = value)
    }

    suspend fun isLogged1() : Boolean {
        return userRepo.isLogged1()
    }

   suspend fun isLogged2() : Boolean{
        return userRepo.isLogged2()
    }

//    suspend fun isLoggedIn() : Boolean{
//        return (isLogged1() && isLogged2())
//    }



}