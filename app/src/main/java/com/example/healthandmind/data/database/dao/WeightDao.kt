package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.healthandmind.data.database.entities.Weight

@Dao
interface WeightDao {

    // non ci possono essere più weight in un giorno dunque weight diventa primary key
    @Query("select * from weight where date = :date")
    suspend fun getWeightByDate(date: String): Weight

    @Query("select * from weight")
    suspend fun getAllWeights(): List<Weight>

    @Query("update weight set dailyWeight = :weight where id = :id")
    suspend fun updateWeight(id : Int ,weight: Double)

    @Insert
    suspend fun insertWeight(weight: Weight)
}