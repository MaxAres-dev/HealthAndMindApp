package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healthandmind.data.database.entities.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food)

    @Query("SELECT * FROM food WHERE mealType = :mealType AND date = :date")
    fun getMeal(mealType:String, date : String): Flow<List<Food>>

    @Query ("update food set servingWeight = :qty where id = :id")
    suspend fun update(id: Int, qty: Double)

    @Query("SELECT servingWeight FROM food WHERE id = :id")
    suspend fun getQty(id: Int): Double

    @Query("SELECT * FROM food WHERE date = :date")
     fun getAllMealsByDate(date: String): Flow<List<Food>>


    @Query("SELECT * FROM food WHERE id = :id")
    suspend fun getMealById(id: Int): Food

    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)

}