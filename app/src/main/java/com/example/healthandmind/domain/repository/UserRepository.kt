package com.example.healthandmind.domain.repository

import com.example.healthandmind.domain.entities.LoginInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun saveUserData(userInfo: LoginInfo)
    suspend fun getUserData(): LoginInfo
    suspend fun updateUserData(userInfo: LoginInfo)
    suspend fun updateIsLogged1(isLogged1 : Boolean)
    suspend fun updateIsLogged2(isLogged2 : Boolean)
    suspend fun isLogged1() : Boolean
    suspend fun isLogged2() : Boolean
    suspend fun updateGoal(goal: String)
    fun getUserGoal() : Flow<String>
//    suspend fun isLoggedIn() : Boolean
}