package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthandmind.data.database.entities.Training
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTraining(training : Training)

    @Delete
    suspend fun deleteTraining(training: Training)


    @Query ( " update training set trainingTime = :time , trainingKcal = :calories  where id = :id")
    suspend fun updateTraining(id: Int, time: Int, calories : Double)

    @Query ("Select * from training where date =:date") // implementazione temporale da fare
    suspend fun getTrainingList(date : String): List<Training>

    @Query ("Select * from training where date =:date") // implementazione temporale da fare
    fun observeTrainingList(date : String): Flow<List<Training>>
}