package com.example.healthandmind.di

import android.content.Context
import androidx.room.Room
import com.example.healthandmind.data.database.AppDatabase
import com.example.healthandmind.data.database.dao.FoodDao
import com.example.healthandmind.data.database.dao.TrainingCalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class DatabaseModule {
    companion object {
        @Singleton
        @Provides
        fun provideDatabase(
            @ApplicationContext context: Context
        ): AppDatabase {
            return Room
                .databaseBuilder(context, AppDatabase::class.java, "healthandmind.db")
                .fallbackToDestructiveMigration()
                .build()
        }

        @Singleton
        @Provides
        fun provideTrainingCalDao(
            database: AppDatabase
        ): TrainingCalDao = database.trainingCalDao()

        @Singleton
        @Provides
        fun provideFoodDao(
            database: AppDatabase
        ): FoodDao = database.foodDao()

        @Singleton
        @Provides
        fun provideUserDao(
            database: AppDatabase
        ) = database.userDao()

        @Singleton
        @Provides
        fun provideTrainingDao(
            database: AppDatabase
        ) = database.trainingDao()

        @Singleton
        @Provides
        fun provideWeightDao(
            database: AppDatabase
        ) = database.weightDao()

        @Singleton
        @Provides
        fun provideWaterDao(
            database: AppDatabase
        ) = database.waterDao()
    }
}