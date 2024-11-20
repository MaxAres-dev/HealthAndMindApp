package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.healthandmind.data.database.entities.Water
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Query("SELECT * FROM Water where date = :date")
    fun getWaterByDate(date: String): Flow<Water>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(water: Water)

    @Query("UPDATE Water SET quantity = :quantity WHERE id = :id")
    suspend fun updateWater(id : Int, quantity : Int)

    @Query("SELECT * FROM Water ORDER BY id DESC limit 1")
    suspend fun getLastElement(): Water
}