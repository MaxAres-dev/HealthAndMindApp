package com.example.healthandmind.data.repository.local

import com.example.healthandmind.data.database.dao.UserDao
import com.example.healthandmind.data.database.entities.User
import com.example.healthandmind.data.loginInfoToUser
import com.example.healthandmind.domain.entities.LoginInfo
import com.example.healthandmind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun saveUserData(userInfo: LoginInfo) {
        userDao.insert(loginInfoToUser(userInfo))
    }

    override suspend fun updateUserData(userInfo: LoginInfo) {
        userDao.update(
            User(
                id = 1,
                name = userInfo.name,
                email = userInfo.email,
                weight = userInfo.weight,
                height = userInfo.height,
                age = userInfo.age,
                sex = userInfo.sex,
                activityLevel = userInfo.activityLevel,
                goal = userInfo.goal,
                isLogged1 = userInfo.isLogged1,
                isLogged2 = userInfo.isLogged2
            )
        )
    }

    override suspend fun getUserData(): LoginInfo {
        userDao.getUserData().let {
            return LoginInfo(
                name = it.name,
                email = it.email,
                weight = it.weight,
                height = it.height,
                age = it.age,
                sex = it.sex,
                activityLevel = it.activityLevel,
                goal = it.goal,
                isLogged1 = it.isLogged1,
                isLogged2 = it.isLogged2
            )
        }
    }

    override suspend fun updateIsLogged1(isLogged1: Boolean) {
        userDao.updateIsLogged1(isLogged1)
    }

    override suspend fun updateIsLogged2(isLogged2: Boolean) {
        userDao.updateIsLogged2(isLogged2)
    }

    override suspend fun isLogged1() : Boolean {
        return userDao.getIsLogged1()
    }

    override suspend fun isLogged2() : Boolean {
        return userDao.getIsLogged2()
    }

    override suspend fun updateGoal(goal: String) {
        userDao.updateGoal(goal)
    }

    override fun getUserGoal(): Flow<String> {
        return userDao.getUserGoal()
    }

//    override suspend fun isLoggedIn() : Boolean {
//        return (isLogged1() && isLogged2())
//
//    }
}