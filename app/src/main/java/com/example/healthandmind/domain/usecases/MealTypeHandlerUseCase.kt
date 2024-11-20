package com.example.healthandmind.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealTypeHandlerUseCase @Inject constructor() {

    // implementare sto usecase come saveMeal e implementare un altro use case per fare observe del flow di dati salvati

    private val MealTypeFlow: MutableStateFlow<String?> = MutableStateFlow(null)


    fun setMealType(qualcosa: String?) {
        MealTypeFlow.value = qualcosa
    }

    fun observeMealType() = MealTypeFlow.asStateFlow()
}
