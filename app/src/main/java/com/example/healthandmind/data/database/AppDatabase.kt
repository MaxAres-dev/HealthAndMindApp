package com.example.healthandmind.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthandmind.data.database.dao.FoodDao
import com.example.healthandmind.data.database.dao.SettingsDao
import com.example.healthandmind.data.database.dao.TrainingCalDao
import com.example.healthandmind.data.database.dao.TrainingDao
import com.example.healthandmind.data.database.dao.UserDao
import com.example.healthandmind.data.database.dao.WaterDao
import com.example.healthandmind.data.database.dao.WeightDao
import com.example.healthandmind.data.database.entities.Food
import com.example.healthandmind.data.database.entities.Settings
import com.example.healthandmind.data.database.entities.Training
import com.example.healthandmind.data.database.entities.TrainingCal
import com.example.healthandmind.data.database.entities.User
import com.example.healthandmind.data.database.entities.Water
import com.example.healthandmind.data.database.entities.Weight

@Database(
    entities = [Food::class, Settings::class, Training::class, TrainingCal::class, User::class, Water::class, Weight::class],
    version = 23,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun settingsDao(): SettingsDao
    abstract fun trainingDao(): TrainingDao
    abstract fun trainingCalDao(): TrainingCalDao
    abstract fun userDao(): UserDao
    abstract fun waterDao(): WaterDao
    abstract fun weightDao(): WeightDao
}

