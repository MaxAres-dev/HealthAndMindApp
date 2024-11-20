package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.healthandmind.data.database.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userInfo: User)

    @Update
    suspend fun update(userInfo: User)

    @Query("SELECT * FROM user where id = 1")
    suspend fun getUserData(): User

    @Delete
    suspend fun delete(userInfo: User)

    @Query("SELECT isLogged1 FROM User WHERE id=1")
    suspend fun getIsLogged1() : Boolean

    @Query(" UPDATE User SET isLogged1 = :isLogged1 WHERE id=1")
    suspend fun updateIsLogged1(isLogged1 : Boolean)

    @Query("SELECT isLogged2 FROM User WHERE id=1")
    suspend fun getIsLogged2() : Boolean

    @Query("update User SET isLogged2 = :isLogged2 WHERE id=1")
    suspend fun updateIsLogged2(isLogged2 : Boolean)

    @Query("UPDATE User SET goal = :goal WHERE id=1")
    suspend fun updateGoal(goal: String)

    @Query("SELECT goal FROM User WHERE id=1")
    fun getUserGoal() : Flow<String>


}