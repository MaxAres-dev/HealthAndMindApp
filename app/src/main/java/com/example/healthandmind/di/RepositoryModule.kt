package com.example.healthandmind.di

import com.example.healthandmind.data.repository.local.FoodRepositoryImpl
import com.example.healthandmind.data.repository.local.MealRepositoryImpl
import com.example.healthandmind.data.repository.local.TrainingCalRepositoryImpl
import com.example.healthandmind.data.repository.local.TrainingRepositoryImpl
import com.example.healthandmind.data.repository.local.UserRepositoryImpl
import com.example.healthandmind.data.repository.local.WaterRepositoryImpl
import com.example.healthandmind.data.repository.local.WeightsRepositoryImpl
import com.example.healthandmind.domain.repository.FoodRepository
import com.example.healthandmind.domain.repository.MealRepository
import com.example.healthandmind.domain.repository.TrainingCalRepository
import com.example.healthandmind.domain.repository.TrainingRepo
import com.example.healthandmind.domain.repository.UserRepository
import com.example.healthandmind.domain.repository.WaterRepository
import com.example.healthandmind.domain.repository.WeightsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTrainingCalRepository(
        implementation: TrainingCalRepositoryImpl
    ) : TrainingCalRepository

    @Singleton
    @Binds
    abstract fun bindFoodRepository(
        implementation: FoodRepositoryImpl
    ): FoodRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        implementation: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindMealRepository(
        implementation: MealRepositoryImpl
    ): MealRepository

    @Singleton
    @Binds
    abstract fun bindTrainingRepository(
        implementation: TrainingRepositoryImpl
    ): TrainingRepo

    @Singleton
    @Binds
    abstract fun bindWeightsRepository(
        implementation: WeightsRepositoryImpl
    ): WeightsRepository

    @Singleton
    @Binds
    abstract fun bindWaterRepository(
        implementation: WaterRepositoryImpl
    ): WaterRepository
}
