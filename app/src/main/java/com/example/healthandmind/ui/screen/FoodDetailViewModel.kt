package com.example.healthandmind.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.Food
import com.example.healthandmind.domain.entities.FoodSelected
import com.example.healthandmind.domain.usecases.FoodIdHandlerUseCase
import com.example.healthandmind.domain.usecases.GetMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val getMealUseCase: GetMealUseCase,
    private val foodIdHandlerUseCase: FoodIdHandlerUseCase
) : ViewModel() {

    data class FoodDetailUiState(val food: Food)

    val _uiState = MutableStateFlow(FoodDetailUiState(Food(0.0, "", 0.0, 0.0, 0.0, 0.0, 0.0)))
    val uiState = _uiState.asStateFlow()

    init {
        updateUiState()
    }

    private fun updateUiState() {
        viewModelScope.launch {
            foodIdHandlerUseCase.observeIdFlow().value.let {
                getMealUseCase.getMealById(it).let { food ->
                    _uiState.update {
                        it.copy(
                            food = Food(
                                name = food.name,
                                calories = food.calories,
                                protein = food.proteinCoeff * food.servingWeight,
                                carbs = food.carbsCoeff * food.servingWeight,
                                fat = food.fatCoeff * food.servingWeight,
                                servingWeight = food.servingWeight,
                                kCalCoeff = food.kCalCoeff
                            )
                        )
                    }
                }
            }
        }
    }
}
