package com.example.healthandmind.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthandmind.domain.entities.Meal
import com.example.healthandmind.domain.entities.TrainingDomain
import com.example.healthandmind.domain.entities.WaterProgress
import com.example.healthandmind.domain.entities.WeightDomain
import com.example.healthandmind.domain.usecases.GetMealUseCase
import com.example.healthandmind.domain.usecases.GetNewWeightUseCase
import com.example.healthandmind.domain.usecases.GetTdeeUseCase
import com.example.healthandmind.domain.usecases.GetUserDataUseCase
import com.example.healthandmind.domain.usecases.TrainingHandlerUseCase
import com.example.healthandmind.domain.usecases.WaterHandlerUseCase
import com.example.healthandmind.domain.usecases.WeightFlow
import com.example.healthandmind.domain.usecases.WeightHandlerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val weightHandlerUseCase: WeightHandlerUseCase,
    private val getNewWeightUseCase: GetNewWeightUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getTdeeUseCase: GetTdeeUseCase,
    private val getMealUseCase: GetMealUseCase,
    private val trainingHandlerUseCase: TrainingHandlerUseCase,
    private val waterHandler: WaterHandlerUseCase,
    private val weightFlow : WeightFlow
) : ViewModel() {

    var _uiState = MutableStateFlow(
        DashboardUiState()
    )
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            launch {
                val currentWeight = weightHandlerUseCase.getLastElement()
                if (LocalDate.now().toString() !=currentWeight.date) {
                    insertWeight()
                }
                weightFlow.setId(currentWeight.id) // serve per sincronizzare il grafico dei pesi con il peso che cambio dallarea personale
                updateWeight()
            }
            launch {
                if (waterHandler.getLastElement().date != LocalDate.now().toString()) {
                    waterHandler.saveWater(WaterProgress(quantity = 0, date = LocalDate.now().toString()))
                }
                refreshDailyWater()
            }
            launch {
                trainingHandlerUseCase.getTrainingList().collectLatest { trainingList ->
                    _uiState.update { it.copy(trainingList = trainingList) }
                    updateCalories()
                }
            }
            launch {
                getMealUseCase.getAllMealByDate(LocalDate.now().toString()).collectLatest { mealList ->
                    _uiState.update { it.copy(mealList = mealList) }
                    updateCalories()
                    updateMacros()
                }
            }
            launch {
                getUserDataUseCase.getUserGoal().collectLatest { goal ->
                    _uiState.update { it.copy(currentGoal = goal) }
                }
            }
            launch {updateUserData()}
        }
    }

    fun onDismiss(value : Boolean) {
        _uiState.update { it.copy(openBottomSheet = value) }
    }

    fun onWaterClicked () {
        _uiState.update { it.copy(openBottomSheet = true) }
    }

    private suspend fun refreshDailyWater() {
        waterHandler.getWaterByDate(date = LocalDate.now().toString())
            .collectLatest { updatedWater -> _uiState.update {
                    it.copy(waterProgress = updatedWater, updatableWater = updatedWater.quantity)
                }
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun insertWeight() {
        weightHandlerUseCase.insertWeight(
            WeightDomain(
                value = getNewWeightUseCase.getNewWeight(),
                date = LocalDate.now().toString()
            )
        )
    }

    fun onWaterQtyIncrease() {
        _uiState.update { it.copy(updatableWater = _uiState.value.updatableWater + 250) }
    }

    fun onWaterQtyDecrease() {
        _uiState.update { it.copy(updatableWater = _uiState.value.updatableWater - 250) }
    }

    fun onWaterSave() {
        viewModelScope.launch {
            waterHandler.updateWater(
                id = _uiState.value.waterProgress.id,
                qty = _uiState.value.updatableWater
            )
        }
    }

    private fun updateMacros() {
        val meals = _uiState.value.mealList
        val totalMacro = meals.foods.sumOf { (it.proteinCoeff + it.carbsCoeff + it.fatCoeff)*it.servingWeight }
        val proteinVal = meals.foods.sumOf { it.proteinCoeff * it.servingWeight }
        val carbsVal = meals.foods.sumOf { it.carbsCoeff * it.servingWeight }
        val fatsVal = meals.foods.sumOf { it.fatCoeff * it.servingWeight }
        _uiState.value =
            _uiState.value.copy(
                macros = Macros(
                    protein = MacroVal(
                        value = proteinVal.toFloat(),
                        percentage = (proteinVal / totalMacro)
                    ),
                    carbs = MacroVal(
                        value = carbsVal.toFloat(),
                        percentage = (carbsVal / totalMacro)
                    ),
                    fats = MacroVal(
                        value = fatsVal.toFloat(),
                        percentage = (fatsVal / totalMacro)
                    )
                )
            )

    }

    private suspend fun updateCalories() {
        val tdee = getTdeeUseCase.getTdee()
        val currentGoal = when (_uiState.value.currentGoal) {
            "Bulk" -> 500
            "Cut" -> -300
            else -> 0
        }
        val totalFoodCalories = _uiState.value.mealList.foods.sumOf { it.kCalCoeff * it.servingWeight }
        val totalTrainingCalories = _uiState.value.trainingList.sumOf { it.trainingKcal }
        _uiState.update {
            it.copy(
                foodsCalories = totalFoodCalories,
                trainingCalories = totalTrainingCalories,
                progressBarPercentage = (((totalFoodCalories - totalTrainingCalories) / (tdee + currentGoal)) * 360).toFloat(),
                goalCalories = tdee + currentGoal
            )
        }
    }

    private suspend fun updateWeight() {
        _uiState.update { it.copy(weight = weightHandlerUseCase.getWeeklyWeights()) }

    }

    private suspend fun updateUserData() {
        _uiState.update {
            it.copy(userName = getUserDataUseCase.getUserData().name)
        }
    }
}

data class DashboardUiState(
    val weight: List<WeightDomain> = emptyList(),
    val progressBarPercentage: Float = 0.0f,
    val mealList : Meal = Meal(foods = emptyList()),
    val trainingList : List<TrainingDomain> = emptyList(),
    val macros: Macros = Macros(),
    val userName: String = "Utente",
    val foodsCalories: Double = 0.0,
    val trainingCalories: Double = 0.0,
    val waterProgress: WaterProgress = WaterProgress(),
    val updatableWater : Int = 0,
    val openBottomSheet : Boolean = false,
    val currentGoal : String = "Normocaloric",
    val goalCalories : Double = 0.0
)

data class Macros(
    val protein: MacroVal = MacroVal(),
    val carbs: MacroVal = MacroVal(),
    val fats: MacroVal = MacroVal(),
)

data class MacroVal(
    val percentage: Double = 0.0,
    val value: Float = 0f
)
