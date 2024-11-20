package com.example.healthandmind.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.domain.usecases.MealTypeHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val mealTypeHandlerUseCase : MealTypeHandlerUseCase
): ViewModel() {

    data class DiaryUiState(
        val mealType : String,
        val openBottomSheet : Boolean = false
    )

    val _uiState: MutableStateFlow<DiaryUiState> = MutableStateFlow(DiaryUiState(MealType.default.default))
    val uiState = _uiState.asStateFlow()

    fun onDismiss(value : Boolean) {
        _uiState.update { it.copy(openBottomSheet = value) }
    }

    fun onWaterClicked(){
        _uiState.update { it.copy(openBottomSheet = true) }
    }

    fun onMealSelect () {
        mealTypeHandlerUseCase.setMealType(_uiState.value.mealType)
    }

    fun updateUiState(mealType : String) {
            _uiState.update { it.copy(mealType = mealType)}
        }
    }

data class MealType(
    val default : String,
    val breakfast: String,
    val launch: String,
    val dinner: String,
    val snack: String
) {
    companion object {
        val default = MealType(
            default = "default",
            breakfast = "Breakfast",
            launch = "Launch",
            dinner = "Dinner",
            snack = "Snack"
        )
    }
}


/*
        (response.body()?.let { it.foodResponses.firstOrNull()?.foodName ?: "No food name" }
            ?: "error food").let { foodName ->
            internalUiState.update { it.copy(foodName = foodName) }



            if (response.isSuccessful) {
                (response.body()?.foodResponses ?: NutrientResponse.default).let { foodList ->
                    _uiState.update { it.copy(foodName = foodList.first().foodName) } }

            }
        }

         */
