package com.example.healthandmind.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthandmind.data.database.entities.TrainingCal

@Dao
interface TrainingCalDao {
    @Insert
    suspend fun insert(
        training1: TrainingCal = TrainingCal(0, "Basketball", 0.1, 1.33),
        training2: TrainingCal = TrainingCal(0, "Soccer", 0.12, 1.43),
        training3: TrainingCal = TrainingCal(0, "Volleyball", 0.05, 1.0),
        training4: TrainingCal = TrainingCal(0, "Aerobics", 0.083, 1.4),
        training5: TrainingCal = TrainingCal(0, "Cycling", 0.1, 1.67),
        training6: TrainingCal = TrainingCal(0, "Gymnastics", 0.067, 1.0),
        training7: TrainingCal = TrainingCal(0, "Weightlifting", 0.5, 2.0),
        training8: TrainingCal = TrainingCal(0, "Boxing", 0.1, 2.0),
        training9: TrainingCal = TrainingCal(0, "Judo/Karate/Kickboxing", 0.167, 1.0),
        training10: TrainingCal = TrainingCal(0, "Running", 0.13, 1.88),
        training11: TrainingCal = TrainingCal(0, "Swimming", 0.1, 1.67),
        training12: TrainingCal = TrainingCal(0, "Stetching/Yoga/Pilates", 0.067, 1.0)
    )

    @Query("Select coefficienteCal from TrainingCal where tipo = :tipo") // check per vedere se funziona
    suspend fun getTrainingCoefficent(tipo: String) : Double// il primo corrisponde a c cal il secondo a c int

    @Query("Select coefficienteInt from TrainingCal where tipo = :tipo")
    suspend fun getTrainingIntensity(tipo: String) : Double

    @Query("Select * from TrainingCal where id between 1 and 12") // potenzialmente potremmo toglierla ma siamo in testing
    suspend fun getTrainingTypes(): List<TrainingCal>
}